import java.io.File;
import java.util.Date;

import org.apache.commons.io.FileUtils;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.TextField;
import org.apache.lucene.document.Field.Store;
import org.apache.lucene.document.LongField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.IndexWriterConfig.OpenMode;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;

import org.apache.lucene.analysis.en.EnglishAnalyzer;
import org.wltea.analyzer.lucene.IKAnalyzer;

class Index{
	// 创建索引
    public void Index_files(int en_cn,String index_dir,String doc_dir) throws Exception {
        // 新建一个索引库
        Directory directory = FSDirectory.open(new File(index_dir));
        // 新建分析器对象
        Analyzer analyzer;
        if(en_cn==0)
        	analyzer = new EnglishAnalyzer();
        else
        	analyzer = new IKAnalyzer();
        // 新建配置对象
        IndexWriterConfig config = new IndexWriterConfig(Version.LUCENE_4_10_4, analyzer);
        config.setOpenMode(OpenMode.CREATE);
        // 创建一个IndexWriter对象（参数一个索引库，一个配置）
        IndexWriter indexWriter = new IndexWriter(directory, config);
        // 创建域对象
        File f = new File(doc_dir);
        File[] list = f.listFiles();
        for (File file : list) {
            // 创建一个文档对象
            Document document = new Document();
            // 文件名称
            String file_name = file.getName();
            Field fileNameField = new TextField("file_Name", file_name, Store.YES);
            // 文件大小
            long file_size = FileUtils.sizeOf(file);
            Field fileSizeField = new LongField("file_Size", file_size, Store.YES);
            // 文件内容
            String file_content = FileUtils.readFileToString(file);
            Field fileContentField = new TextField("file_Content", file_content, Store.YES);

            // 添加到document
            document.add(fileNameField);
            document.add(fileSizeField);
            document.add(fileContentField);

            // 创建索引
            indexWriter.addDocument(document);
        }
        // 关闭资源
        indexWriter.close();
    }
}

public class my_lucene {
	public static void main(String []args) throws Exception {
		Index a = new Index();
		//开始时间
		Date start = new Date();
		System.out.println("Indexing...");
		a.Index_files(0,"C:\\Users\\ljh\\OneDrive\\大三下\\互联网搜索引擎\\课程设计2\\En_index","C:\\Users\\ljh\\OneDrive\\大三下\\互联网搜索引擎\\课程设计2\\En_processed");
		a.Index_files(1,"C:\\Users\\ljh\\OneDrive\\大三下\\互联网搜索引擎\\课程设计2\\Ch_index","C:\\Users\\ljh\\OneDrive\\大三下\\互联网搜索引擎\\课程设计2\\Ch_processed");
		//结束时间
		Date end = new Date();
		System.out.println("done.");
		System.out.println("total milliseconds:"+(end.getTime() - start.getTime()));
	}
}
