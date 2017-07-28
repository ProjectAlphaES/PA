package es.projectalpha.pa.rage.cmd;

import es.projectalpha.pa.core.api.PAUser;
import es.projectalpha.pa.core.cmd.PACmd;
import es.projectalpha.pa.core.utils.Utils;
import es.projectalpha.pa.rage.RageGames;

import java.util.Arrays;

public class PointSetCMD extends PACmd {

    public PointSetCMD() {
        super("pointset", Grupo.Admin, Arrays.asList("ponerpunto", "ps"));
    }

    private RageGames plugin = RageGames.getInstance();
    int p;
    @Override
    public void run(PAUser user, String label, String[] args) {
        if (args.length == 0) {
            p = this.plugin.getFiles().getConfig().getInt("puntos") + 1;
            this.plugin.getFiles().getConfig().set("puntos", p);
            this.plugin.getFiles().getConfig().set("spawns." + p, Utils.locationToString(user.getLoc()));
            this.plugin.getFiles().saveFiles();
            user.sendMessage("&aPunto puesto satisfactoriamente " + "&6" + p);
        }
    }
}
