/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GraphVisualizations;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 *
 * @author Preetham Reddy
 */
public class Animation {
    Process p;
    public Animation() throws IOException{
        Path currentRelativePath = (Path) Paths.get("");
        String s = currentRelativePath.toAbsolutePath().toString();
        String path = s + File.separator+"visualizer.py";
        
        ProcessBuilder pb = new ProcessBuilder("python",path);
        p = pb.start();
        
    }
}
