
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Scanner;


public class Main {

    public static void main(String[] args) throws Exception {
        Scanner in = new Scanner(System.in);

        System.out.println("Enter the name of the picture with the maze you want to solve (it must be on inputMazes directory and in png format):");
        String inputFileName = in.nextLine();

        if (!inputFileName.substring(inputFileName.length() - 4, inputFileName.length()).equals(".png")){
            inputFileName += ".png";
        }

        File imageFile = new File("inputMazes/" + inputFileName);

        BufferedImage image = ImageIO.read(imageFile);


        System.out.println("Solving...");

        Maze maze = new Maze(image);
        BufferedImage sol = maze.solve();

        File outputImage = new File("outputMazes/" + "output" + inputFileName);
        ImageIO.write(sol, "png", outputImage);

        System.out.println("Done :)");

    }
}
