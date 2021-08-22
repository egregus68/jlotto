/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.gregus.jlotto.file;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Grzegorz
 */
public class FileUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(FileUtil.class);

    public void writeToText(Map<Integer, String> in, String fileName) {
        FileChannel channel;
        try (RandomAccessFile stream = new RandomAccessFile(fileName, "rw")) {
            channel = stream.getChannel();

            for (Map.Entry<Integer, String> entry : in.entrySet()) {

                byte[] strBytes = (entry.getValue() + System.getProperty("line.separator")).getBytes();
                ByteBuffer buffer = ByteBuffer.allocate(strBytes.length);

                buffer.put(strBytes);
                buffer.flip();
                channel.write(buffer);
            }
            channel.close();
        } catch (FileNotFoundException ex) {
            LOGGER.error(ex.getMessage(), ex);
        } catch (IOException ex) {
            LOGGER.error(ex.getMessage(), ex);
        }
    }

    public void writeToText(List<String> in, String fileName) {
        FileChannel channel;
        try (RandomAccessFile stream = new RandomAccessFile(fileName, "rw")) {
            channel = stream.getChannel();

            for (String entry : in) {

                byte[] strBytes = (entry + System.getProperty("line.separator")).getBytes();
                ByteBuffer buffer = ByteBuffer.allocate(strBytes.length);

                buffer.put(strBytes);
                buffer.flip();
                channel.write(buffer);
            }
            channel.close();
        } catch (FileNotFoundException ex) {
            LOGGER.error(ex.getMessage(), ex);
        } catch (IOException ex) {
            LOGGER.error(ex.getMessage(), ex);
        }
    }


}
