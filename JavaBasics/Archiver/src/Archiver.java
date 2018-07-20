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
    private static void compress (String[] args) throws NotValidInput {
        if (!validToCompress(args)) {
            throw new NotValidInput();
        }
        try {
            ZipOutputStream zipout = new ZipOutputStream(new FileOutputStream(args[1]));
            for (int i = 2; i < args.length; ++i) {
                FileInputStream fin = new FileInputStream(args[i]);
                ZipEntry entry = new ZipEntry(args[i]);
                zipout.putNextEntry(entry);
                byte[] buffer = new byte[fin.available()];
                fin.read(buffer);
                zipout.write(buffer);
                zipout.closeEntry();
                fin.close();
            }
            zipout.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    private static void extract (String[] args) throws NotValidInput {
        if (!validToExtract(args)) {
            throw new NotValidInput();
        }
        try {
            ZipInputStream zipin = new ZipInputStream(new FileInputStream(args[1]));
            ZipEntry entry;
            while ((entry = zipin.getNextEntry()) != null) {
                String name = entry.getName();
                FileOutputStream fout = new FileOutputStream(args.length==3?args[2].concat(name):name);
                for (int c = zipin.read(); c != -1; c = zipin.read()) {
                    fout.write(c);
                }
                fout.flush();
                zipin.closeEntry();
                fout.close();
            }
        } catch(Exception ex) {
            ex.printStackTrace();
        }
    }
    private static void add (String[] args) throws NotValidInput {
        if (!validToAdd(args)) {
            throw new NotValidInput();
        }
    }
    private static void comment (String[] args) throws NotValidInput {
        if (!validToComment(args)) {
            throw new NotValidInput();
        }
        if (args[2].equals("-r")) {
            try {
                ZipFile zip = new ZipFile(new File(args[2]), ZipFile.OPEN_READ);
                System.out.println(zip.getComment());
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        } else if (args[2].equals("-a")) {
            try {
                ZipOutputStream zipout = new ZipOutputStream(new FileOutputStream(args[2]));
                zipout.setComment(args[3]);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }
    private static boolean validToCompress (String[] args) {
        return true;
    }
    private static boolean validToExtract (String[] args) {
        return true;
    }
    private static boolean validToComment (String[] args) {
        return true;
    }
    private static boolean validToAdd (String[] args) {
        return true;
    }
    public static void main(String[] args) {
        if (args != null && args[0].equals("--compress")) {
            try {
                compress(args);
            } catch (NotValidInput e){
                System.err.println();
            }
        } else if (args != null && args[0].equals("--extract")) {
            try {
                extract(args);
            } catch (NotValidInput e){
                System.err.println();
            }
        } else if (args != null && args[0].equals("--comment")) {
            try {
                comment(args);
            } catch (NotValidInput e){
                System.err.println();
            }
        } else if (args != null && args[0].equals("--add")) {
            try {
                add(args);
            } catch (NotValidInput e){
                System.err.println();
            }
        } else {
            instruction();
        }
    }
}
