import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

class NotValidInput extends Exception {}
public class Archiver {
    private static void instruction () {
        System.out.println("Instruction");
    }
    private static void compress (String[] args){
        try {
            ZipOutputStream zout = new ZipOutputStream(new FileOutputStream(args[1]));
            for (int i = 2; i < args.length; ++i) {
                FileInputStream fin = new FileInputStream(args[i]);
                ZipEntry entry = new ZipEntry(args[i]);
                zout.putNextEntry(entry);
                byte[] buffer = new byte[fin.available()];
                fin.read(buffer);
                zout.write(buffer);
                zout.closeEntry();
                fin.close();
            }
            zout.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    private static void extract (String[] args){
        try {
            ZipInputStream zin = new ZipInputStream(new FileInputStream(args[1]));
            ZipEntry entry;
            while ((entry = zin.getNextEntry()) != null) {
                String name = entry.getName();
                FileOutputStream fout = new FileOutputStream(args.length==3?args[2].concat(name):name);
                for (int c = zin.read(); c != -1; c = zin.read()) {
                    fout.write(c);
                }
                fout.flush();
                zin.closeEntry();
                fout.close();
            }
        } catch(Exception ex) {
            ex.printStackTrace();
        }
    }
    private static void add (String[] args){
        try {
            ZipInputStream zin = new ZipInputStream(new FileInputStream(args[1]));
            ZipOutputStream zout = new ZipOutputStream(new FileOutputStream("temporary.zip"));
            ZipEntry entry;
            while ((entry = zin.getNextEntry()) != null) {

            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    private static void comment (String[] args){
        if (args[2].equals("-r")) {
            try {
                ZipFile zip = new ZipFile(new File(args[2]), ZipFile.OPEN_READ);
                System.out.println(zip.getComment());
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        } else if (args[2].equals("-a")) {
            try {
                ZipOutputStream zout = new ZipOutputStream(new FileOutputStream(args[2]));
                zout.setComment(args[3]);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }
    public static void main(String[] args) {
        if (args != null && args[0].equals("--compress")) {
            compress(args);
        } else if (args != null && args[0].equals("--extract")) {
            extract(args);
        } else if (args != null && args[0].equals("--comment")) {
            comment(args);
        } else if (args != null && args[0].equals("--add")) {
            add(args);
        } else {
            instruction();
        }
    }
}
