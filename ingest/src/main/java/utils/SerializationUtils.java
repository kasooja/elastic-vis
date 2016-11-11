package utils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.Serializable;

import org.apache.commons.io.FileUtils;


public class SerializationUtils {

	@SuppressWarnings("unchecked")
	public static <T> T readObject(File file) {
		try {
			ObjectInputStream ois = new ObjectInputStream(FileUtils.openInputStream(file));
			return (T) ois.readObject();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		
	}
	
	/**
	 * Reads an object from the given input stream
	 * @param is the input stream to read from
	 * @return the read object
	 */
	@SuppressWarnings("unchecked")
	public static <T> T readObject(InputStream is) {
		try {
			ObjectInputStream ois = new ObjectInputStream(is);
			return (T) ois.readObject();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		
	}



    /**
     * Converts the given object to a byte array
     * @param toSave the object to save
     */
    public static byte[] toByteArray(Serializable toSave) {
        try {
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            ObjectOutputStream os = new ObjectOutputStream(bos);
            os.writeObject(toSave);
            byte[] ret = bos.toByteArray();
            os.close();
            return ret;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }


	/**
	 * Writes the object to the output stream
	 * THIS DOES NOT FLUSH THE STREAM
	 * @param toSave the object to save
	 * @param writeTo the output stream to write to
	 */
	public static void writeObject(Serializable toSave,OutputStream writeTo) {
		try {
			ObjectOutputStream os = new ObjectOutputStream(writeTo);
			os.writeObject(toSave);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		
	}
	
	public static void saveObject(Object toSave,File saveTo) {
		try {
			OutputStream os1 = FileUtils.openOutputStream(saveTo);
			ObjectOutputStream os = new ObjectOutputStream(os1);
			os.writeObject(toSave);
			os.flush();
            os.close();
            os1.close();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		
	}
}