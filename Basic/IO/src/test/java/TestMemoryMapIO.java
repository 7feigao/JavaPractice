import org.junit.Test;

import java.io.IOException;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Paths;
import java.util.zip.CRC32;

public class TestMemoryMapIO {
    @Test
    public void testMemoryMapIO(){
        try {
            FileChannel fileChannel=FileChannel.open(Paths.get("F:\\repos\\JavaPractice\\Basic\\IO\\custom.bat"));
            long fileSize=fileChannel.size();
            MappedByteBuffer mappedByteBuffer=fileChannel.map(FileChannel.MapMode.READ_ONLY,0,fileSize);
            CRC32 crc32=new CRC32();
            byte[] bytes=new byte[10240];
            for (int i = 0; i < fileSize; i++) {
                crc32.update(mappedByteBuffer.get(i));
            }
            System.out.println(crc32.getValue());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
