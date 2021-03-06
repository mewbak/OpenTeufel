package org.openteufel.file.cel;

import java.nio.ByteBuffer;

public class PALFile
{
    private final int[]     rgba;

    public PALFile(ByteBuffer in)
    {
        rgba = new int[256];
        for(int i = 0; i < 256; i++)
        {
            rgba[i] = packColor(in.get(), in.get(), in.get(), 255);
        }
    }
    
    public static int packColor(int r, int g, int b, int a)
    {
        return ((a & 0xFF) << 24) | ((r & 0xFF) << 16) | ((g & 0xFF) << 8) | (b & 0xFF);
        //return ((r&0xFF) << 24) | ((g&0xFF) << 16) | ((b&0xFF) << 8) | (a&0xFF);
    }
    
    public int[] getColors()
    {
        return rgba;
    }
    
    public int getColor(int i)
    {
        return rgba[i&0xFF];
    }
}
