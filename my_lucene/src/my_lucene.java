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
	// ��������
    public void Index_files(int en_cn,String index_dir,String doc_dir) throws Exception {
        // �½�һ��������
        Directory directory = FSDirectory.open(new File(index_dir));
        // �½�����������
        Analyzer analyzer;
        if(en_cn==0)
        	analyzer = new EnglishAnalyzer();
        else
        	analyzer = new IKAnalyzer();
        // �½����ö���
        IndexWriterConfig config = new IndexWriterConfig(Version.LUCENE_4_10_4, analyzer);
        config.setOpenMode(OpenMode.CREATE);
        // ����һ��IndexWriter���󣨲���һ�������⣬һ�����ã�
        IndexWriter indexWriter = new IndexWriter(directory, config);
        // ���������
        File f = new File(doc_dir);
        File[] list = f.listFiles();
        for (File file : list) {
            // ����һ���ĵ�����
            Document document = new Document();
            // �ļ�����
            String file_name = file.getName();
            Field fileNameField = new TextField("file_Name", file_name, Store.YES);
            // �ļ���С
            long file_size = FileUtils.sizeOf(file);
            Field fileSizeField = new LongField("file_Size", file_size, Store.YES);
            // �ļ�����
            String file_content = FileUtils.readFileToString(file);
            Field fileContentField = new TextField("file_Content", file_content, Store.YES);

            // ��ӵ�document
            document.add(fileNameField);
            document.add(fileSizeField);
            document.add(fileContentField);

            // ��������
            indexWriter.addDocument(document);
        }
        // �ر���Դ
        indexWriter.close();
    }
}

public class my_lucene {
	public static void main(String []args) throws Exception {
		Index a = new Index();
		//��ʼʱ��
		Date start = new Date();
		System.out.println("Indexing...");
		a.Index_files(0,"C:\\Users\\ljh\\OneDrive\\������\\��������������\\�γ����2\\En_index","C:\\Users\\ljh\\OneDrive\\������\\��������������\\�γ����2\\En_processed");
		a.Index_files(1,"C:\\Users\\ljh\\OneDrive\\������\\��������������\\�γ����2\\Ch_index","C:\\Users\\ljh\\OneDrive\\������\\��������������\\�γ����2\\Ch_processed");
		//����ʱ��
		Date end = new Date();
		System.out.println("done.");
		System.out.println("total milliseconds:"+(end.getTime() - start.getTime()));
	}
}
