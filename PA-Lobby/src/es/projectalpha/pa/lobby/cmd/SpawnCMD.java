package es.projectalpha.pa.lobby.cmd;

import es.projectalpha.pa.core.PACore;
import es.projectalpha.pa.core.api.PAData;
import es.projectalpha.pa.core.api.PAUser;
import es.projectalpha.pa.core.cmd.PACmd;
import es.projectalpha.pa.core.utils.Messages;
import es.projectalpha.pa.lobby.PALobby;
import es.projectalpha.pa.lobby.utils.Helpers;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class SpawnCMD extends PACmd {

    public SpawnCMD() {
        super("spawn", Grupo.Usuario);
    }

    public void run(PAUser user, String label, String[] args) {
        if (args.length == 0) {
            new Helpers(user).sendToSpawn();
            return;
        }

        if(args.length == 1){
            if(!user.isOnRank(Grupo.Mod)) return;
            Player p = Bukkit.getPlayerExact(args[0]);
            if(!p.isOnline()) user.sendMessage("&4El jugador no existe o no está conectado.");
            new Helpers((PAUser) p).sendToSpawn();
            return;
        }
        user.sendMessage(Messages.getMessage(Messages.BUFF_ARGS, PAData.CORE));
    }
}
