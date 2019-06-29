package cn.itcast.lucene;


import org.apache.commons.io.FileUtils;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.document.*;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.Term;
import org.apache.lucene.search.NumericRangeQuery;
import org.apache.lucene.search.Query;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;
import org.junit.Test;
import org.wltea.analyzer.lucene.IKAnalyzer;


import java.io.File;
import java.io.IOException;

public class CreateIndex {

	/*
	    创建索引库
     */
    @Test
	public void createIndex() throws Exception{
		System.out.println("新建索引库");
		System.out.println("新建索引库222222222222");

		System.out.println("新建索引库555555555555555555");
		String directory = "D:\\heimaDemo\\indexDataBase";
		//指定索引库目录位置
		FSDirectory fsDirectory = FSDirectory.open(new File(directory));
		//对文档内容进行分词
		Analyzer analyzer = new IKAnalyzer();//IK分词器
		//索引库对象的配置信息(版本,分词器)
		IndexWriterConfig config = new IndexWriterConfig(Version.LUCENE_4_10_3,analyzer);

		//创建索引库对象(指定索引库目录,配置信息)
		IndexWriter indexWriter = new IndexWriter(fsDirectory,config);

		//创建索引库数据源
		File dataSource = new File("D:\\heimaDemo\\seacher");
		//获取文件数组
		File[] files = dataSource.listFiles();
		for (File file : files) {
			//创建文档对象
			Document document = new Document();
			//构建文档对象域字段
			document.add(new TextField("fileName",file.getName(),Field.Store.YES));
			document.add(new TextField("fileContext", FileUtils.readFileToString(file),Field.Store.YES));
			document.add(new LongField("fileSize",FileUtils.sizeOf(file),Field.Store.YES));
			//域字段类型,默认不分词存储
			document.add(new StringField("filePath",file.getPath(),Field.Store.YES));
			//放入索引库中
            indexWriter.addDocument(document);
		}
		//提交
		indexWriter.commit();
		//释放
		indexWriter.close();
	}

	@Test
	public void deleteIndex() throws IOException {
		String directory = "D:\\heimaDemo\\indexDataBase";
		//指定索引库目录位置
		FSDirectory fsDirectory = FSDirectory.open(new File(directory));
		//对文档内容进行分词
		Analyzer analyzer = new IKAnalyzer();//IK分词器
		//索引库对象的配置信息(版本,分词器)
		IndexWriterConfig config = new IndexWriterConfig(Version.LUCENE_4_10_3,analyzer);

		//创建索引库对象(指定索引库目录,配置信息)
		IndexWriter indexWriter = new IndexWriter(fsDirectory,config);

		//根据词条删除
		indexWriter.deleteDocuments(new Term("fileName","传智播客"));

		//根据查询条件删除
		Query query = NumericRangeQuery.newLongRange("fileSize",0L,50L,true,true);
		indexWriter.deleteDocuments(query);
		indexWriter.commit();
		indexWriter.close();
	}
}
