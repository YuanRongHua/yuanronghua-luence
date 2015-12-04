package com.apache.yuanronghua;

import java.io.IOException;
import java.nio.file.Paths;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.Term;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.junit.Test;

public class SearchFile {

	@Test
	public void searchTxtFile() throws IOException, ParseException {
		Analyzer analyzer = new StandardAnalyzer();
		Directory dir = FSDirectory.open(Paths.get("D:\\luceneIndex"));
		IndexReader reader = DirectoryReader.open(dir);
		IndexSearcher indexSearcher = new IndexSearcher(reader);
		// QueryParser parser = new QueryParser("contents", analyzer);
		// Query query = parser.parse("DEBUG");
		Term term = new Term("contents", "DEBUG");
		TermQuery luceneQuery = new TermQuery(term);
		TopDocs topDocs = indexSearcher.search(luceneQuery, 10000);
		System.out.println("总共匹配多少个：" + topDocs.totalHits);
		ScoreDoc[] hits = topDocs.scoreDocs;
		for (int i = 0; i < hits.length; i++) {
			Document document = indexSearcher.doc(hits[i].doc);
			System.out.println(document.get("contents"));
		}
	}
}
