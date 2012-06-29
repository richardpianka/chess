package com.richardpianka.chess.common

import java.io.File

object Utilities {
  def delete(file: File) {
    if (file.isDirectory)
      file.listFiles().foreach(delete(_))
    else
      file.delete
  }
}

//
//  public static boolean deleteDir(File dir) {
//    if (dir.isDirectory()) {
//      String[] children = dir.list();
//      for (int i=0; i<children.length; i++) {
//        boolean success = deleteDir(new File(dir, children[i]));
//        if (!success) {
//          return false;
//        }
//        }
//        }
//
//        // The directory is now empty so delete it
//        return dir.delete();
//        }
//}
