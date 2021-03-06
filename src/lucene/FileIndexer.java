package lucene;

import java.io.File;
import java.io.FileFilter;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import org.apache.lucene.analysis.en.EnglishAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.FieldType;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexOptions;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

import main.EventLog;

/**
 * This class takes text files and creates indexes from them that can be searched
 * @author Ryan Hudson
 * @version 1.0
 */
public class FileIndexer {

	IndexWriter writer;

	/**
	 * Constructor takes a filepath
	 * @param indexDirectoryPath
	 * @throws IOException
	 */
	public FileIndexer(String indexDirectoryPath) throws IOException {
		//this directory will contain the indexes
		Directory docDir = FSDirectory.open(Paths.get(indexDirectoryPath));

		EnglishAnalyzer analyzer = new EnglishAnalyzer();

		IndexWriterConfig iwc = new IndexWriterConfig(analyzer);
		iwc.setOpenMode(IndexWriterConfig.OpenMode.CREATE);
		writer = new IndexWriter(docDir, iwc);
	}

	/**
	 * Method that creates a document that can be analyzed
	 * @param f - The text file that is being indexed
	 * @return The document
	 * @throws IOException
	 */
	public Document addDoc(File f) throws IOException {
		Document doc = new Document();
		FileReader fileReader = new FileReader(f);
		FieldType ft = new FieldType();
		ft.setIndexOptions(IndexOptions.DOCS_AND_FREQS_AND_POSITIONS_AND_OFFSETS);

		doc.add(new Field(LuceneConstants.CONTENTS, fileReader, ft));
		doc.add(new TextField(LuceneConstants.FILE_NAME, f.getName(), Field.Store.YES));
		doc.add(new TextField(LuceneConstants.FILE_PATH, f.getCanonicalPath(), Field.Store.YES));

		return doc;
	}

	/**
	 * Closes the IndexWrtier
	 */
	public void closeIndex() { 
		try {  
			writer.close(); 
		} catch (Exception e) { 
			System.out.println("Got an Exception: " + e.getMessage()); 
		} 
	}

	/**
	 * Creates a Document then adds the Document to the IndexWriter
	 * @param f - File being indexed
	 * @throws IOException
	 */
	public void indexFile(File f) throws IOException {
		Document doc = addDoc(f);
		writer.addDocument(doc);
	}

	/**
	 * Creates the index using a file path of where the data is located and a file filter
	 * @param filesName - List of text file names
	 * @param filter - Filter for a specific file format
	 * @return The number of files that were indexed.
	 * @throws IOException
	 */
	public int createIndex(ArrayList<String> fileNames, FileFilter filter) 
			throws IOException{
		File file;
		//a bunch of checks to see if the file can be used for indexing
		for (String fileName : fileNames) {
			file = new File(fileName);
			if(!file.isDirectory() && !file.isHidden() && file.exists() && file.canRead() && filter.accept(file)) {
				//EventLog.writeFileIndexed(fileName);
				indexFile(file);
			}
		}
		return writer.numDocs();
	}
}

