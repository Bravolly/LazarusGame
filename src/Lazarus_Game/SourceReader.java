package Lazarus_Game;

import java.io.*;

/**
 *  This class is used to manage the source program input stream;
 *  each read request will return the next usable character; it
 *  maintains the source column position of the character
 */
public class SourceReader {
    private BufferedReader source;
    private int lineno = 0,   // line number of source program
            position;     // position of last character processed
    private boolean isPriorEndLine = true;  // if true then last character read was newline
    // so read in the next line
    private String nextLine;
    private String memory = ""; //
/*
    public static void main(String args[]) {
        SourceReader s = null;
        try {
            s = new SourceReader("t");
            while (true) {
                char ch = s.read();
                System.out.println("Char: " + ch + " Line: " + s.lineno +
                         "position: " + s.position);
            }
        } catch (Exception e) {}

        if (s != null) {
            s.close();
        }
    }
*/

    /**
     *  Construct a new SourceReader
     *  @param sourceFile the String describing the user's source file
     *  @exception IOException is thrown if there is an I/O problem
     */
    public SourceReader(String sourceFile) throws IOException {
        System.out.println("Source file: "+sourceFile);
        System.out.println("user.dir: " + System.getProperty("user.dir"));
        source = new BufferedReader(new FileReader(sourceFile));
    }

    void close() {
        try {
            source.close();
        } catch (Exception e) {}
    }

    /**
     *  read next char; track line #, character position in line<br>
     *  return space for newline
     *  @return the character just read in
     *  @IOException is thrown for IO problems such as end of file
     */
    public char read() throws IOException {
        if (isPriorEndLine) {
            lineno++;
            position = -1;
            nextLine = source.readLine();
            if (nextLine != null) {
                //System.out.println("READLINE: "+nextLine);   // lexer readline printout
                memory += lineno + ". " + nextLine + '\n';   // store current line
            }
            isPriorEndLine = false;
        }
        if (nextLine == null) {  // hit eof or some I/O problem
            print();
            throw new IOException();
        }
        if ( nextLine.length() == 0) {
            isPriorEndLine = true;
            return ' ';
        }
        position++;
        if (position >= nextLine.length()) {
            isPriorEndLine = true;
            return ' ';
        }
        return nextLine.charAt(position);
    }

    /**
     *  read previous char; track line #, character position in line<br>
     *  return space for newline
     *  @return the character just read in
     *  @IOException is thrown for IO problems such as end of file
     */
    public char readBack() {
        if ( isPriorEndLine == true )
            isPriorEndLine = false;
        position--;
        return nextLine.charAt(position);
    }

    /**
     *  @return the position of the character just read in
     */
    public int getPosition() {
        return position;
    }

    /**
     *  @return the line number of the character just read in
     */
    public int getLineno() {
        return lineno;
    }

    /**
     *  @return true if next character is end of line
     */
    public boolean checkEndLine() {
        return isPriorEndLine;
    }

    /**
     *  prints out all lines that have been read
     */
    public void print() {
        System.out.println("\n" + memory);
        return;
    }
}
