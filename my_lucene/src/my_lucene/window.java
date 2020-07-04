package my_lucene;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.en.EnglishAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;
import java.awt.event.ActionListener;
import java.io.File;
import java.awt.event.ActionEvent;

public class window extends JFrame {

	private JPanel contentPane;
	private JTextField textField;
	private JTextField textField_1;
	private JTextField textField_2;
	private JTextField textField_3;
	private JTextField textField_4;
	private JTextField textField_5;
	private JTextField textField_6;
	
	private JTextArea textArea1;
	private JTextArea textArea2;
	private JTextArea textArea3;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					window frame = new window();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public window() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 748, 631);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("\u8F93\u5165\u67E5\u8BE2\u5173\u952E\u8BCD\uFF1A");
		lblNewLabel.setBounds(27, 0, 114, 29);
		contentPane.add(lblNewLabel);
		
		textField = new JTextField();
		textField.setBounds(27, 39, 468, 29);
		contentPane.add(textField);
		textField.setColumns(10);
		
		JButton btnNewButton = new JButton("\u67E5\u8BE2");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					Search("C:\\Users\\ljh\\OneDrive\\大三下\\互联网搜索引擎\\课程设计2\\En_index",textField.getText());
				}
				catch(Exception ex) {
					System.out.println(ex.getMessage());
				}
			}
		});
		btnNewButton.setBounds(571, 39, 108, 29);
		contentPane.add(btnNewButton);
		
		JLabel lblNewLabel_1 = new JLabel("\u5339\u914D\u5EA6\u524D\u4E09\u7684\u6587\u6863\uFF1A");
		lblNewLabel_1.setBounds(27, 93, 114, 29);
		contentPane.add(lblNewLabel_1);
		
		textField_1 = new JTextField();
		textField_1.setBounds(27, 124, 310, 29);
		contentPane.add(textField_1);
		textField_1.setColumns(10);
		
		textField_2 = new JTextField();
		textField_2.setBounds(575, 124, 129, 29);
		contentPane.add(textField_2);
		textField_2.setColumns(10);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(27, 153, 677, 112);
		contentPane.add(scrollPane);
		
		textArea1 = new JTextArea();
		textArea1.setLineWrap(true);
		scrollPane.setViewportView(textArea1);
		
		textField_3 = new JTextField();
		textField_3.setColumns(10);
		textField_3.setBounds(27, 275, 310, 29);
		contentPane.add(textField_3);
		
		textField_4 = new JTextField();
		textField_4.setColumns(10);
		textField_4.setBounds(575, 275, 129, 29);
		contentPane.add(textField_4);
		
		textField_5 = new JTextField();
		textField_5.setColumns(10);
		textField_5.setBounds(27, 425, 310, 29);
		contentPane.add(textField_5);
		
		textField_6 = new JTextField();
		textField_6.setColumns(10);
		textField_6.setBounds(575, 425, 129, 29);
		contentPane.add(textField_6);
		
		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(27, 303, 677, 112);
		contentPane.add(scrollPane_1);
		
		textArea2 = new JTextArea();
		textArea2.setLineWrap(true);
		scrollPane_1.setViewportView(textArea2);
		
		JScrollPane scrollPane_1_1 = new JScrollPane();
		scrollPane_1_1.setBounds(27, 454, 677, 112);
		contentPane.add(scrollPane_1_1);
		
		textArea3 = new JTextArea();
		textArea3.setLineWrap(true);
		scrollPane_1_1.setViewportView(textArea3);
	}
	
	// 搜索索引
    public void Search(String index_dir,String query_str) throws Exception {
        // 第一步：创建一个Directory对象，也就是索引库存放的位置。
        Directory directory = FSDirectory.open(new File(index_dir));
        // 第二步：创建一个indexReader对象，需要指定Directory对象。
        IndexReader indexReader = DirectoryReader.open(directory);
        // 第三步：创建一个indexSearcher对象，需要指定IndexReader对象
        IndexSearcher indexSearcher = new IndexSearcher(indexReader);
        // 第四步：创建一个TermQuery对象，指定查询的域和查询的关键词。
        Analyzer analyzer = new EnglishAnalyzer();
        QueryParser parser = new QueryParser("file_Content", analyzer);
        query_str=query_str.trim();
        Query query = parser.parse(query_str);
        // 第五步：执行查询（显示条数5）
        TopDocs topDocs = indexSearcher.search(query, 5);
        // 第六步：返回查询结果。遍历查询结果并输出。
        ScoreDoc[] scoreDocs = topDocs.scoreDocs;
        for (int i=0; i<3 ; i++) {
            int doc = scoreDocs[i].doc;
            Document document = indexSearcher.doc(doc);
            // 文件名称
            String fileName = document.get("file_Name");
            // 文件内容
            String fileContent = document.get("file_Content");
            if(i==0) {
            	textField_1.setText(fileName);
            	textField_2.setText(Float.toString(scoreDocs[i].score));
            	textArea1.setText(fileContent);
            }
            else if(i==1) {
            	textField_3.setText(fileName);
            	textField_4.setText(Float.toString(scoreDocs[i].score));
            	textArea2.setText(fileContent);
            }
            else if(i==2) {
            	textField_5.setText(fileName);
            	textField_6.setText(Float.toString(scoreDocs[i].score));
            	textArea3.setText(fileContent);
            }
        }
        // 第七步：关闭IndexReader对象
        indexReader.close();
    }
}
