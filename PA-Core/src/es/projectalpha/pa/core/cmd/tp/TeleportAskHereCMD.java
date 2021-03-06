package es.projectalpha.pa.core.cmd.tp;

import es.projectalpha.pa.core.api.PAServer;
import es.projectalpha.pa.core.api.PAUser;
import es.projectalpha.pa.core.cmd.PACmd;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import java.util.Arrays;
import java.util.List;

public class TeleportAskHereCMD extends PACmd {

    public TeleportAskHereCMD() {
        super("tpahere", Grupo.Builder, Arrays.asList("teleportaskhere"));
    }

    @Override
    public void run(PAUser user, String label, String[] args) {
        if (args.length == 0) {
            user.sendMessage("");
            return;
        }

        PAUser target = PAServer.getUser(plugin.getServer().getPlayerExact(args[0]));
        if (!target.isOnline()) {
            userNotOnline(user);
            return;
        }

        PAServer.addTeleportHereRequest(target.getName(), user.getName());
        if (PAServer.getTeleportRequests().containsKey(target.getName())) {
            PAServer.getTeleportRequests().remove(target.getName());
        }

        PAServer.getTeleportRequests().keySet().stream()
                .filter(u -> PAServer.getTeleportRequests().get(u).equals(target.getName()))
                .forEach(u -> PAServer.removeTeleportRequest(u));

        target.sendMessage("");
        user.sendMessage("");

        //Eliminar petición a los 2 minutos
        plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, () -> {
            if (PAServer.getTeleportRequests().containsKey(target.getName()) && PAServer.getTeleportRequests().get(target.getName()).equals(user.getName())) {
                PAServer.removeTeleportRequest(target.getName());
                user.sendMessage("");
            }
        }, 120 * 20L);
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command cmd, String alias, String[] args, String curs, Integer curn) {
        return null;
    }
}
