package es.projectalpha.pa.sur.api;

import es.projectalpha.pa.core.PACore;
import es.projectalpha.pa.core.api.PAData;
import es.projectalpha.pa.core.api.PAUser;
import es.projectalpha.pa.core.utils.ScoreboardUtil;
import es.projectalpha.pa.sur.PASurvival;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.scheduler.BukkitRunnable;

import java.text.DecimalFormat;

public class SurvivalUser extends PAUser {

    private PASurvival plugin = PASurvival.getInstance();

    @Getter @Setter private boolean score = true;

    public SurvivalUser(String name) {
        super(name);
    }

    public void setInfo() {
        DecimalFormat df = new DecimalFormat("#.#");
        ScoreboardUtil board = new ScoreboardUtil(PAData.SURVIVAL.getOldPrefix(), "sur");
        new BukkitRunnable() {
            @Override
            public void run() {
                if (getPlayer() == null || !score) cancel();
                String world;
                switch (getLoc().getWorld().getName()) {
                    case "world":
                        world = "§2Survival";
                    case "world_nether":
                        world = "§cNether";
                    case "world_the_end":
                        world = "§dEnd";
                    case "Recursos":
                        world = "§aRecursos";
                    case "Eventos":
                        world = "§6Eventos";
                    default:
                        world = "§bError";
                }

                board.setName(PAData.SURVIVAL.getOldPrefix());
                board.text(4, "§aDinero: §e" + Double.valueOf(df.format(plugin.getVault().getBalance(getOfflinePlayer()))));
                board.text(3, "§e ");
                board.text(2, "§aMundo: " + world);
                board.text(1, "§e ");
                board.text(0, PACore.getOLD_IP());
                if (getPlayer() != null && score) board.build(getPlayer());
            }
        }.runTaskTimer(plugin, 0, 1);
    }
}
