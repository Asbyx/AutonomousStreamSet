package io.github.asbyx.autonomousstreamset.obs_files;

import java.io.File;

public class OBSFilesUpdater {
    //the files
    private static File name1;
    private static File name2;
    private static File score1;
    private static File score2;
    private static File round;

    /**
     * Start the OBS files updater.
     * Open the files to be ready to write in them.
     */
    public static void start() {
        // open the files
        name1 = new File("./obs-files/name1.txt");
        name2 = new File("./obs-files/name2.txt");
        score1 = new File("./obs-files/score1.txt");
        score2 = new File("./obs-files/score2.txt");
        round = new File("./obs-files/round.txt");

        // create the files if they don't exist
        try {
            name1.createNewFile();
            name2.createNewFile();
            score1.createNewFile();
            score2.createNewFile();
            round.createNewFile();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Update the files by overwriting them with the new data.
     * @param data the data to write in the files
     *             data[0] = name1
     *             data[1] = name2
     *             data[2] = score1
     *             data[3] = score2
     *             data[4] = round
     * @return null if the update was successful, the error message otherwise
     */
    public static String updateFiles(String[] data) {
        // write the data in the files directly (overwrite)
        try {
            // write the data in the files
            OBSFilesUpdater.writeInFile(name1, data[0]);
            OBSFilesUpdater.writeInFile(name2, data[1]);
            OBSFilesUpdater.writeInFile(score1, data[2]);
            OBSFilesUpdater.writeInFile(score2, data[3]);
            OBSFilesUpdater.writeInFile(round, data[4]);
            return null;
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    /**
     * Write the data in the file.
     * @param file the file to write in
     * @param data the data to write
     */
    private static void writeInFile(File file, String data) {
        // write the data in the file
        try {
            // write the data in the file
            java.io.FileWriter writer = new java.io.FileWriter(file);
            writer.write(data);
            writer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Close the files.
     */
    public static void close() {
        // close the files
        name1 = null;
        name2 = null;
        score1 = null;
        score2 = null;
        round = null;
    }
}
