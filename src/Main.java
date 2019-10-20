
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class Main {

    public static void main(String[] args) throws Exception {
        Scanner in = new Scanner(System.in);

        System.out.println("Enter the path of the picture with the maze you want to solve (it must be in png format):");
        String inputFilePath = in.nextLine();

        if (inputFilePath.length() < 5 || !inputFilePath.substring(inputFilePath.length() - 4, inputFilePath.length()).equals(".png")){
            inputFilePath += ".png";
        }

        File imageFile = new File(inputFilePath);

        BufferedImage image = ImageIO.read(imageFile);


        System.out.println("Solving...");

        Maze maze = new Maze(image);
        BufferedImage sol = maze.solve();




        File outputImage = new File(inputFilePath.substring(0, inputFilePath.length()-4) + "OUTPUT.png");
        ImageIO.write(sol, "png", outputImage);

        System.out.println("Done :)");

    }
}
