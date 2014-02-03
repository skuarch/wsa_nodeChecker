package model.net;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import javax.ws.rs.core.MediaType;
import org.jboss.resteasy.client.*;

/**
 * Wrapper to RestEasy library.
 *
 * @author skuarch
 */
@SuppressWarnings( "deprecation" )
public final class ClientRestfulPost implements ClientRestful {
    
    private ClientRequest clientRequest = null;    
    private ClientResponse<String> clientResponse = null;
    private int status = 0;
    private boolean posted = false;
    private boolean sended = false;

    //==========================================================================
    /**
     * create a instance.
     *
     * @param url String
     */
    @SuppressWarnings( "deprecation" )
    public ClientRestfulPost(String url) {

        clientRequest = new ClientRequest(url);
        clientRequest.accept(MediaType.APPLICATION_JSON);

    } // end ClientRestful

    //==========================================================================
    /**
     * send a post data to the server.
     *
     * @param text String
     * @throws Exception
     */
    @Override
    public void send(String text) throws Exception {

        if (text == null || text.length() < 0) {
            text = "";
        }

        clientRequest.body(MediaType.APPLICATION_JSON, text);
        sended = true;

    } // end send    

    //==========================================================================
    /**
     * receive data from server.
     *
     * @return String
     * @throws Exception
     */
    @Override
    public String receive() throws Exception {        
        
        if(!sended){
            throw new Exception("please call the method send before you receive");
        }
        
        if(!posted){
            throw new Exception("please call the method post after you send");
        }
        
        StringBuilder sb = new StringBuilder();
        String inputString = null;
        BufferedReader br = null;
        ByteArrayInputStream bais = null;
        InputStreamReader isr = null;

        try {

            status = clientResponse.getStatus();

            if (status != 200) {
                throw new Exception("bad response from server " + status);
            }

            bais = new ByteArrayInputStream(clientResponse.getEntity().getBytes("UTF-8"));
            isr = new InputStreamReader(bais,"UTF-8");
            br = new BufferedReader(isr);

            while ((inputString = br.readLine()) != null) {
                sb.append(inputString);
            }

        } catch (Exception e) {
            throw e;
        } finally {
            closeBufferedReader(br);
            closeInputStream(bais);
            closeInputStreamReader(isr);
        }

        return sb.toString();

    } // end receive

    //==========================================================================
    public void post() throws Exception {

        if (posted) {
            throw new Exception("you already post");
        }

        if (clientRequest == null) {
            throw new Exception("clientRequest is null");
        }

        posted = true;

        clientResponse = clientRequest.post(String.class);
    }

    //==========================================================================
    public void closeClient() {

        if (clientResponse != null) {
            clientResponse.close();
        }

    }

    //==========================================================================
    /**
     * close BufferedReader
     *
     * @param bufferedReader BufferedReader
     */
    private void closeBufferedReader(BufferedReader bufferedReader) {

        try {
            if (bufferedReader != null) {
                bufferedReader.close();
            }
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }

    } // end closeBufferedReader

    //==========================================================================
    /**
     * close InputStream.
     *
     * @param inputStream
     */
    private void closeInputStream(InputStream inputStream) {

        try {
            if (inputStream != null) {
                inputStream.close();
            }
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }

    } // end closeInputStream

    //==========================================================================
    /**
     * close InputStreamReader.
     *
     * @param inputStream
     */
    private void closeInputStreamReader(InputStreamReader inputStreamReader) {

        try {
            if (inputStreamReader != null) {
                inputStreamReader.close();
            }
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }

    } // end closeInputStream

} // end class
