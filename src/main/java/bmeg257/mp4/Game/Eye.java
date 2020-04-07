package main.java.bmeg257.mp4.Game;

public class Eye extends Monster {

    public Eye(){
        super(10,20,2, "Floating Eye");
        String[] taunts = new String[3];
        taunts[0] = "Eye see you!";
        taunts[1] = "Damn, I dropped my contact...";
        taunts[2] = "Wait how am I speaking without a mouth?";
        String gfx = "           _ . - = - . _\n" +
                "       . \"  \\  \\   /  /  \" .\n" +
                "     ,  \\                 /  .\n" +
                "   . \\   _,.--~=~\"~=~--.._   / .\n" +
                "  ;  _.-\"  / \\ !   ! / \\  \"-._  .\n" +
                " / ,\"     / ,` .---. `, \\     \". \\\n" +
                "/.'   `~  |   /:::::\\   |  ~`   '.\\\n" +
                "\\`.  `~   |   \\:::::/   | ~`  ~ .'/\n" +
                " \\ `.  `~ \\ `, `~~~' ,` /   ~`.' /\n" +
                "  .  \"-._  \\ / !   ! \\ /  _.-\"  .\n" +
                "   ./    \"=~~.._  _..~~=`\"    \\.\n" +
                "     ,/         \"\"          \\,\n" +
                "       . _/             \\_ .\n" +
                "          \" - ./. .\\. - \"\n";
        setGfx(gfx);
        setTaunts(taunts);
    }
}
