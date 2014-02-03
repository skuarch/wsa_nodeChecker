package model.common;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 *
 * @author skuarch
 */
public class ExecuteCommand {

    //==========================================================================
    public static String exec(String command) throws IOException, InterruptedException {

        Process process;
        int exitValue;
        String line = null;

        try {

            process = Runtime.getRuntime().exec(command);
            process.waitFor();
            exitValue = process.exitValue();

            if (exitValue == 0) {

                line = readReturnCommand(process.getInputStream());

            } else {

                line = readReturnCommand(process.getErrorStream());

            }
            
            process.destroy();

        } catch (IOException | InterruptedException e) {
            throw e;
        }finally{
            process = null;
        }

        return line;

    } // end exec

    //==========================================================================
    private static String readReturnCommand(InputStream inputStream) throws IOException {

        String line;
        StringBuilder lines = new StringBuilder();

        try {

            try (                    
                    InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "UTF-8");
                    BufferedReader bufferedReader = new BufferedReader(inputStreamReader);) {

                while ((line = bufferedReader.readLine()) != null) {
                    lines.append(line);
                }

            }

        } catch (IOException e) {
            throw e;
        } finally {
            inputStream.close();
        }

        return lines.toString();

    } // end readReturnCommand

} // end class
