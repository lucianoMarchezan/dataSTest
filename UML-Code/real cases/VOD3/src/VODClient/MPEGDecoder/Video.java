/* Decompiled by Jasmine from Video2.class */
/* Originally compiled from Video2.java */

package MPEGDecoder;

import java.io.*;
import java.awt.Label;
import java.net.URL;
import java.util.Date;

public class Video implements Runnable 
{
    BitInputStream instream;
    GOP gop;
    SequenceHeader header;
    DataStore store;
    URL movieurl;
    VODClient vod;
    Label fps;
    long starttime;
    //static float picture_rate[] = {0.0f,23.9760f,24.0f,25.0f,29.97f,30.0f,50.0f,59.94f,60.0f};
	static float picture_rate[] = {10.0f,11.0f,12.0f,13.0f,14.0f,15.0f,16.0f,17.0f,18.0f,19.0f};

    public Video(BorderPanel borderPanel, Label label1, Label label2, VODClient vODClient)
    {
        store = new DataStore(borderPanel);
        header = new SequenceHeader();
        store.setsequenceheader(header);
        gop = new GOP(instream, store, label2);
        instream = null;
        vod = vODClient;
        fps = label1;
    }

    final boolean parse_sequence_header()
    {
        if (instream.readint() != 435)
            return false;
        header.horizontal_size = instream.readbits(12);
        header.mb_width = (header.horizontal_size + 15) / 16;
        header.vertical_size = instream.readbits(12);
        header.mb_height = (header.vertical_size + 15) / 16;
        header.pel_aspect_ratio = instream.readbits(4);
        header.picture_rate = picture_rate[instream.readbits(4)];
        header.picture_frequency = (int)(1.0F / header.picture_rate * 960.0);
        header.bit_rate = instream.readbits(18);
        header.marker_bit = instream.readbit();
        header.vbv_buffer_size = instream.readbits(10);
        header.constrained_parameter_flag = instream.readbit();
        header.load_intra_quantizer_matrix = instream.readbit();
        if (header.load_intra_quantizer_matrix)
        {
            System.out.println("load_intra_quantizer_matrix");
            for (int i = 0; i < 64; i++)
                store.intra_quantizer_matrix[DataStore.zig_zag_scan[i]] = instream.readbyte();
        }
        header.load_non_intra_quantizer_matrix = instream.readbit();
        if (header.load_non_intra_quantizer_matrix)
        {
            System.out.println("load_non_intra_quantizer_matrix");
            for (int j = 0; j < 64; j++)
                store.non_intra_quantizer_matrix[DataStore.zig_zag_scan[j]] = instream.readbyte();
            store.setnonintra(true);
        }
        else
            store.setnonintra(false);
        instream.next_start_code();
        if (instream.nextaligned(32) == 437)
        {
            instream.flushaligned(32);
            while (instream.nextbits(24) != 1)
                instream.readbyte();
            instream.next_start_code();
        }
        if (instream.nextaligned(32) == 434)
        {
            instream.flushaligned(32);
            while (instream.nextbits(24) != 1)
                instream.readbyte();
            instream.next_start_code();
        }
        store.newsquence();
        return true;
    }

    public final void run()
    {
        if (instream == null)
        {
            if (movieurl == null)
                return;
            try
            {
				if (movieurl.getProtocol().toLowerCase().equals("file"))
				{
					String fileName = movieurl.getHost()+":"+movieurl.getFile();
					instream = new BitInputStream(new FileInputStream(fileName));
				}
				else					
	                instream = new BitInputStream(movieurl.openStream());
				
                gop.setstream(instream);
            }
            catch (IOException e1)
            {
                System.out.println("Error during opening stream");
                return;
            }
        }
        while (true)
        {
            starttime = System.currentTimeMillis();
            gop.clearnumofpic();
            store.clearprevdisptime();
            instream.next_start_code();
            do
            {
                parse_sequence_header();
                do
                    gop.read_gop();
                while (instream.nextaligned(32) == 440);
            }
            while (instream.nextaligned(32) == 435);
            Float floatx = new Float((float)(1000 * gop.getnumofpic()) / (System.currentTimeMillis() - starttime));
            String string = floatx.toString();
            if (string.length() > 5)
                string = string.substring(0, 5);
            fps.setText(string + " fps");
            try
            {
				if (movieurl.getProtocol().toLowerCase().equals("file"))
				{
					String fileName = movieurl.getHost()+":"+movieurl.getFile();
					instream = new BitInputStream(new FileInputStream(fileName));
				}
				else					
	                instream = new BitInputStream(movieurl.openStream());
                gop.setstream(instream);
            }
            catch (IOException e2)
            {
                System.out.println("Error");
                stopmovie();
                vod.stopmovie();
                return;
            }
        }
    }

    public final void setratecontrol(boolean flag)
    {
        store.setratecontrol(flag);
    }

    public final void setvideostream(URL uRL)
    {
        movieurl = uRL;
    }

    public final void stopmovie()
    {
        if (instream == null)
            return;
        Date date = new Date();
        Float floatx= new Float((float)(1000 * gop.getnumofpic()) / (date.getTime() - starttime));
        String string = floatx.toString();
        if (string.length() > 5)
            string = string.substring(0, 5);
        fps.setText(string + " fps");
        try
        {
            instream.close();
        }
        catch (IOException e)
        {
        }
        gop.setstream(null);
        instream = null;
    }
}
