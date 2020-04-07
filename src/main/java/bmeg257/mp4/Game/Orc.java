package main.java.bmeg257.mp4.Game;

public class Orc extends Monster{
    public Orc(){
        super(15,50,3, "Orc");
        String[] taunts = new String[3];
        taunts[0] = "Me hit with club!";
        taunts[1] = "ARRGH! I destroy more!";
        taunts[2] = "You know, I am sick of the stereotype of all Orcs being dumb brutes who are violent.\n" +
                    "It really suppresses us as a species and bars us from entering any prestigious job, forcing us to\n" +
                    "steal to survive.";
        String gfx =
                "                        _,.---''```````'-.\n" +
                "                    ,-'`                  `-._\n" +
                "                 ,-`                   __,-``,\\\n" +
                "                /             _       /,'  ,|/ \\\n" +
                "              ,'         ,''-<_`'.    |  ,' |   \\\n" +
                "             /          / _    `  `.  | / \\ |\\  |\n" +
                "             |         (  |`'-,---, `'  \\_|/ |  |\n" +
                "             |         |`  \\  \\|  /  __,    _ \\ |\n" +
                "             |         |    `._\\,'  '    ,-`_\\ \\|\n" +
                "             |         |        ,----      /|   )\n" +
                "             \\         \\       / --.      {/   /|\n" +
                "              \\         | |       `.\\         / |\n" +
                "               \\        / `-.                 | /\n" +
                "                `.     |     `-        _,--V`)\\/        _,-\n" +
                "                  `,   |           /``V_,.--`  \\.  _,-'`\n" +
                "                   /`--'`._        `-'`         )`'\n" +
                "                  /        `-.            _,.-'`\n" +
                "                              `-.____,.-'`";
        setGfx(gfx);
        setTaunts(taunts);
    }
}
