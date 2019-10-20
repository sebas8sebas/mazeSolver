
public final class RGButils {


    /**
     * Tolerance to qualify an rgb value as gray
     */
    static int GRAYTOLERANCE = 10;

    /**
     * Tolerance to qualify an rgb value as white
     */
    static int WHITETOLERANCE = 10;



    /**
     * Check if RGB value is somewhere in the gray scale
     */
   public static boolean isGray(int[] rgb) {
        int r = rgb[0];
        int g = rgb[1];
        int b = rgb[2];

        return Math.abs(r - g) < GRAYTOLERANCE && Math.abs(r - b) < GRAYTOLERANCE && Math.abs(g - b) < GRAYTOLERANCE;
    }

    /**
     * Checks if rgb value is white
     */
    public static boolean isWhite(int[] rgb){
        return isGray(rgb) && rgb[0] > 255 - WHITETOLERANCE;
    }

    /**
     * Checks if rgb value is black
     */
    public static boolean isBlack(int[] rgb){
        return isGray(rgb) && !isWhite(rgb);
    }

    /**
     * Return if rgb value is start/end marker
     */
    public static boolean isMarker(int[] rgb){
        return !isGray(rgb);
    }

    /**
     * Convert rgb int ot array wih r, g and b values
     * code from stack overflow
     * @param a
     * @return
     */
    public static int[] rgbIntToArray(int a){
        int[] rgb = new int[]{a>>16,a>>8&255,a&255};
        for (int i = 0; i < rgb.length; i++) {
            if (rgb[i] < 0){rgb[i] += 256;}
        }
        return rgb;
    }


    /**
     * Convert rgb array to int value
     * code from stack overflow
     * @param rgb
     * @return
     */
    public static int rgbArrayToInt(int[] rgb){

        rgb[0] = (rgb[0] << 16) & 0x00FF0000;
        rgb[1] = (rgb[1] << 8) & 0x0000FF00;
        rgb[2] = rgb[2] & 0x000000FF;

        return 0xFF000000 | rgb[0] | rgb[1] | rgb[2];
    }


}
