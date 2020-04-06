package main.java.bmeg257.mp4.Game;

public class Goblin extends Monster {

    public Goblin(){
        super(5,10,1, "Goblin");
        String[] taunts = new String[3];
        taunts[0] = "Give gold?!";
        taunts[1] = "Meats on the menu!";
        taunts[2] = "Grrrrr wait what are my morals?";
        String gfx = ",      ,\n" +
                "            /(.-\"\"-.)\\\n" +
                "        |\\  \\/      \\/  /|\n" +
                "        | \\ / =.  .= \\ / |\n" +
                "        \\( \\   o\\/o   / )/\n" +
                "         \\_, '-/  \\-' ,_/\n" +
                "           /   \\__/   \\\n" +
                "           \\ \\__/\\__/ /\n" +
                "         ___\\ \\|--|/ /___\n" +
                "       /`    \\      /    `\\\n" +
                "  ree /       '----'       \\";
        setGfx(gfx);
        setTaunts(taunts);
    }
}
