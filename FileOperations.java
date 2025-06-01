import java.io.*;
import java.util.ArrayList;
import java.util.List;

class FileOperations {
    // Main method to read all records
    public static List<Information> readAllRecords() throws Exception {
        List<Information> records = new ArrayList<>();
        File file = new File("data.ser");
        
        // Return empty list if file doesn't exist or is empty
        if (!file.exists() || file.length() == 0) {
            return records;
        }
        
        // Verify file is valid before reading
        if (!isFileValid(file)) {
            throw new Exception("Database file is corrupted. Please reset the database.");
        }

        try (ObjectInputStream fpt = new ObjectInputStream(new FileInputStream(file))) {
            while (true) {
                try {
                    Information record = (Information) fpt.readObject();
                    records.add(record);
                } catch (EOFException e) {
                    break;
                }
            }
        } catch (InvalidClassException | StreamCorruptedException e) {
            // Handle corrupted file by deleting it
            file.delete();
            throw new Exception("Database was corrupted and has been reset. Please add records again.");
        }
        return records;
    }

    // Method to validate file structure
    private static boolean isFileValid(File file) {
        if (!file.exists() || file.length() == 0) {
            return true;
        }
        
        try (ObjectInputStream test = new ObjectInputStream(new FileInputStream(file))) {
            while (true) {
                try {
                    Object obj = test.readObject();
                    if (!(obj instanceof Information)) {
                        return false;
                    }
                } catch (EOFException e) {
                    return true;
                }
            }
        } catch (Exception e) {
            return false;
        }
    }

    // Safe method to write all records
    public static void writeAllRecords(List<Information> records) throws Exception {
        File file = new File("data.ser");
        File tempFile = new File("data.temp.ser");
        
        // First write to temporary file
        try (ObjectOutputStream fpt = new ObjectOutputStream(new FileOutputStream(tempFile))) {
            for (Information record : records) {
                fpt.writeObject(record);
            }
        }
        
        // Atomic file replacement
        if (file.exists() && !file.delete()) {
            throw new Exception("Could not remove old database file");
        }
        
        if (!tempFile.renameTo(file)) {
            throw new Exception("Failed to save database properly");
        }
    }

    // Safe method to append a single record
    public static void appendRecord(Information record) throws Exception {
        File file = new File("data.ser");
        
        // If file doesn't exist or is empty, use regular write
        if (!file.exists() || file.length() == 0) {
            List<Information> records = new ArrayList<>();
            records.add(record);
            writeAllRecords(records);
            return;
        }
        
        // Otherwise use append mode with special stream
        try (AppendingObjectOutputStream fpt = new AppendingObjectOutputStream(
                new FileOutputStream(file, true))) {
            fpt.writeObject(record);
        }
    }

    // Special class for append-mode serialization
    private static class AppendingObjectOutputStream extends ObjectOutputStream {
        public AppendingObjectOutputStream(OutputStream out) throws IOException {
            super(out);
        }
        
        @Override
        protected void writeStreamHeader() throws IOException {
            reset();
        }
    }
    
    // Utility method to reset database
    public static void resetDatabase() throws Exception {
        File file = new File("data.ser");
        if (file.exists() && !file.delete()) {
            throw new Exception("Could not reset database");
        }
    }
}