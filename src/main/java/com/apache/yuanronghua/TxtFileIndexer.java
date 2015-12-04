package com.apache.yuanronghua;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Paths;

import org.apache.log4j.Logger;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field.Store;
import org.apache.lucene.document.LongField;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.junit.Test;

public class TxtFileIndexer {

	static Logger logger = Logger.getLogger(TxtFileIndexer.class);

	@Test
	public void CreateTxtFileIndex() throws IOException {

		// 创建索引存储的目录文件系统或内存系统
		// Directory directory = new RAMDirectory();
		Directory directory = FSDirectory.open(Paths.get("D:\\luceneIndex"));

		File dataDir = new File("D:\\luceneData");
		File[] dataFiles = dataDir.listFiles();

		if (dataFiles != null) {

			Analyzer analyzer = new StandardAnalyzer();
			IndexWriterConfig iwc = new IndexWriterConfig(analyzer);
			IndexWriter writer = new IndexWriter(directory, iwc);

			for (int i = 0; i < dataFiles.length; i++) {
				Document document = new Document();
				Reader txtReader = new FileReader(dataFiles[i]);

				document.add(new LongField("modified", dataFiles[i]
						.lastModified(), Store.NO));
				document.add(new TextField("contents", txtReader));
				document.add(new StringField("path", dataFiles[i].toString(),
						Store.YES));
				writer.addDocument(document);
				txtReader.close();
			}
			writer.commit();
			writer.close();
		}

	}

}
