package SystemDesign.JavaBasics.FileIO;

import java.io.*;

public class FileIO {
    public static void main(String[] args) {

        FileExample fe = new FileExample();

        try {
//            fe.readFile();
//            fe.writeFile();
//            fe.readAndWrite();
              fe.rwBinaryData();

        } catch (IOException e) {
            System.out.println(e);
        }
    }

}


//Reading file, while ((line = br.readLine()) != null)

class FileExample {
    public void readFile() throws IOException {
        BufferedReader br = new BufferedReader(new FileReader("src/main/java/SystemDesign/JavaBasics/FileIO/sample.txt"));
        String line;

        while ((line = br.readLine()) != null) {
            System.out.println(line);
        }

        br.close();
    }

// Writing to file, new file replacing old one.
    public void writeFile() throws IOException {
        BufferedWriter bw = new BufferedWriter(new FileWriter("src/main/java/SystemDesign/JavaBasics/FileIO/outputs.txt"));

        bw.write("Hello, Java file handling");
        bw.newLine();
        bw.write("This is the second line.");

        bw.close();
    }

    public void readAndWrite() throws IOException {
        BufferedReader br = new BufferedReader(new FileReader("src/main/java/SystemDesign/JavaBasics/FileIO/sample.txt"));
        BufferedWriter bw = new BufferedWriter(new FileWriter("src/main/java/SystemDesign/JavaBasics/FileIO/outputs.txt"));

        String line;

        while (((line = br.readLine()) != null)) {
            bw.write(line);
            bw.newLine();
            System.out.println("FP: " + line);
        }

        br.close();
        bw.close();
    }

    // Reading and writing bytes binary data
    public void rwBinaryData() throws IOException {
        FileInputStream fis = new FileInputStream("src/main/java/SystemDesign/JavaBasics/FileIO/image.png");
        FileOutputStream fos = new FileOutputStream("src/main/java/SystemDesign/JavaBasics/FileIO/copy.png");

        byte[] buffer = new byte[1024]; // 1 KB buffer
        int bytesRead;

        while ((bytesRead = fis.read(buffer)) != -1) {
            fos.write(buffer, 0, bytesRead);
        }

        fis.close();
        fos.close();
    }
}
