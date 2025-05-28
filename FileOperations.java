import java.io.*;
import java.util.ArrayList;
import java.util.List;

class FileOperations {
    public static List<Information> readAllRecords() throws Exception {
        List<Information> records = new ArrayList<>();
        try (ObjectInputStream fpt = new ObjectInputStream(new FileInputStream("data.ser"))) {
            while (true) {
                try {
                    Information record = (Information) fpt.readObject();
                    records.add(record);
                } catch (EOFException e) {
                    break;
                }
            }
        } catch (FileNotFoundException e) {
            throw new Exception("Database not found. Please add records first.");
        } catch (IOException | ClassNotFoundException e) {
            throw new Exception("Error reading database: " + e.getMessage());
        }
        return records;
    }
    
    public static void writeAllRecords(List<Information> records) throws Exception {
        try (ObjectOutputStream fpt = new ObjectOutputStream(new FileOutputStream("data.ser"))) {
            for (Information record : records) {
                fpt.writeObject(record);
            }
        } catch (IOException e) {
            throw new Exception("Error saving to database: " + e.getMessage());
        }
    }
    
    public static void appendRecord(Information record) throws Exception {
        try (AppendingObjectOutputStream fpt = new AppendingObjectOutputStream(new FileOutputStream("data.ser", true))) {
            fpt.writeObject(record);
        } catch (IOException e) {
            throw new Exception("Error adding record: " + e.getMessage());
        }
    }
    
    // Special class to handle append mode correctly
    private static class AppendingObjectOutputStream extends ObjectOutputStream {
        public AppendingObjectOutputStream(OutputStream out) throws IOException {
            super(out);
        }
        
        @Override
        protected void writeStreamHeader() throws IOException {
            // Do not write a header
            reset();
        }
    }
}