package cn.itcast.lucene;


import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.Term;
import org.apache.lucene.queryparser.classic.MultiFieldQueryParser;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.*;
import org.apache.lucene.store.FSDirectory;
import org.junit.Test;
import org.wltea.analyzer.lucene.IKAnalyzer;

import java.io.File;

/*
     * 查询索引库数据*/
public class QueryIndex {

	@Test
	public void queryIndex() throws Exception{

		//获取读取索引库对象
		DirectoryReader reader = DirectoryReader.open(FSDirectory.open(new File("D:\\heimaDemo\\indexDataBase")));
		//获取查询索引库对象
		IndexSearcher indexSearcher = new IndexSearcher(reader);
        //查询所有
		///Query query = new MatchAllDocsQuery();
		//根据词条查询
		//Query query = new TermQuery(new Term("fileName","传智播客"));
		//模糊查询
		//Query query = new WildcardQuery(new Term("fileName","传智播?"));
		//相似度查询
		//Query query = new FuzzyQuery(new Term("fileName","lucen"));
		//数值范围匹配
		Query query = NumericRangeQuery.newLongRange("fileSize",0L,50L,true,true);
		//多条件组合查询
//		BooleanQuery query = new BooleanQuery();
//		Query query1 = new TermQuery(new Term("fileName", "传智播客"));
//		Query query2 = new TermQuery(new Term("fileContext", "lucene"));
//
//        query.add(query1,BooleanClause.Occur.MUST);
//        query.add(query2,BooleanClause.Occur.MUST_NOT);
		//解析查询
		//String term = "传智播客是一家很不错的培训机构";
		//Analyzer analyzer = new IKAnalyzer();
		//QueryParser queryParser = new QueryParser("fileName", analyzer);
		//MultiFieldQueryParser parser = new MultiFieldQueryParser(new String[]{"fileName","fileContent"},analyzer);
		//解析
		//Query query = parser.parse(term);

		//读取索引库(搜索条件,搜索条数)
		TopDocs topDocs = indexSearcher.search(query, 10);
		System.out.println("总条数"+topDocs.totalHits);
		ScoreDoc[] scoreDocs = topDocs.scoreDocs;
		for (ScoreDoc scoreDoc : scoreDocs) {
			System.out.println(scoreDoc.shardIndex);
			//文档ID
			int doc = scoreDoc.doc;
			System.out.println("文档id"+doc);
			Document document = indexSearcher.doc(doc);
			System.out.println("文件名"+document.getField("fileName"));
			System.out.println("文件内容"+document.getField("fileContext"));
			System.out.println("文件长度"+document.getField("fileSize"));
			System.out.println("文件路径"+document.getField("filePath"));
		}
	}


}
