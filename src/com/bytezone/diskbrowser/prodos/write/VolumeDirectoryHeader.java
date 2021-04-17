package com.bytezone.diskbrowser.prodos.write;

import static com.bytezone.diskbrowser.prodos.write.ProdosDisk.UNDERLINE;
import static com.bytezone.diskbrowser.utilities.Utility.readShort;
import static com.bytezone.diskbrowser.utilities.Utility.writeShort;

// -----------------------------------------------------------------------------------//
public class VolumeDirectoryHeader extends DirectoryHeader
// -----------------------------------------------------------------------------------//
{
  int bitMapPointer = 0x06;
  int totalBlocks;

  // ---------------------------------------------------------------------------------//
  public VolumeDirectoryHeader (ProdosDisk disk, byte[] buffer, int ptr)
  // ---------------------------------------------------------------------------------//
  {
    super (disk, buffer, ptr);

    storageType = (byte) 0x0F;
  }

  // ---------------------------------------------------------------------------------//
  @Override
  void read ()
  // ---------------------------------------------------------------------------------//
  {
    super.read ();

    bitMapPointer = readShort (buffer, ptr + 0x23);
    totalBlocks = readShort (buffer, ptr + 0x25);
  }

  // ---------------------------------------------------------------------------------//
  @Override
  void write ()
  // ---------------------------------------------------------------------------------//
  {
    super.write ();

    writeShort (buffer, ptr + 0x23, bitMapPointer);
    writeShort (buffer, ptr + 0x25, totalBlocks);
  }

  // ---------------------------------------------------------------------------------//
  @Override
  public String toString ()
  // ---------------------------------------------------------------------------------//
  {
    StringBuilder text = new StringBuilder ();

    text.append (UNDERLINE);
    text.append ("Volume Directory Header\n");
    text.append (UNDERLINE);
    text.append (super.toString ());
    text.append (String.format ("Bitmap pointer ... %d%n", bitMapPointer));
    text.append (String.format ("Total blocks ..... %d%n", totalBlocks));
    text.append (UNDERLINE);

    return text.toString ();
  }
}