package es.projectalpha.pa.sur.events;

import es.projectalpha.pa.core.api.PAData;
import es.projectalpha.pa.core.utils.Utils;
import es.projectalpha.pa.sur.files.Files;
import es.projectalpha.pa.sur.manager.PvPManager;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.potion.PotionEffectType;

public class PvPEvent implements Listener{

    private Files files = new Files();
    private PvPManager manager = new PvPManager();

    @EventHandler
    public void EntityDamageByEntityEvent(EntityDamageByEntityEvent e){

        Entity damager = e.getDamager();
        Entity en = e.getEntity();

        if (damager instanceof Projectile){
            if (en instanceof Player){
                Projectile pr = (Projectile)damager;

                if(!(pr.getShooter() instanceof Player)) return;

                Player p = (Player)pr.getShooter();
                Player pl = (Player)en;

                if (p.hasMetadata("NPC") || pl.hasMetadata("NPC")) return;

                if(p == pl) return;
                if(Files.user.getBoolean("Users." + p.getName() + ".pvp") == true && Files.user.getBoolean("Users." + pl.getName() + ".pvp") == true){

                    if(p.getGameMode() == GameMode.CREATIVE){
                        p.setGameMode(GameMode.SURVIVAL);
                    }

                    if(p.isFlying()){
                        p.setFlying(false);
                    }

                    if(p.hasPotionEffect(PotionEffectType.INVISIBILITY)){
                        p.getActivePotionEffects().clear();
                    }

                    if(manager.isInPvP(pl)) return;
                    if(manager.isInPvP(p)) return;
                    if(manager.isNewbie(pl)) return;
                    if(manager.isNewbie(p)) return;

                    manager.addPvp(p);
                    manager.addPvp(pl);

                    p.sendMessage(Utils.colorize(PAData.SURVIVAL.getPrefix() + ChatColor.DARK_RED + " Has entrado en pelea con " + ChatColor.DARK_GRAY + pl.getName() + ChatColor.DARK_RED + ", ¡no te desconectes!"));
                    pl.sendMessage(Utils.colorize(PAData.SURVIVAL.getPrefix() + ChatColor.DARK_RED + "Has entrado en pelea con " + ChatColor.DARK_GRAY + p.getName() + ChatColor.DARK_RED + ", ¡no te desconectes!"));

                }else{

                    e.setCancelled(true);
                    e.setDamage(0D);
                    pl.setFireTicks(0);

                    if(manager.isNewbie(p)){
                        p.sendMessage(Utils.colorize(PAData.SURVIVAL.getPrefix() + ChatColor.DARK_GRAY + " Tienes la protección de novato activada, para desactivarla haz /pvp disable."));
                        return;
                    }

                    if(manager.isNewbie(pl)){
                        p.sendMessage(Utils.colorize(PAData.SURVIVAL.getPrefix() + ChatColor.GOLD + pl.getName() + ChatColor.DARK_GRAY + " tiene la protección de novato activada."));
                        return;
                    }

                    if(Files.user.getBoolean("Users." + p.getName() + ".pvp") == false){
                        p.sendMessage(Utils.colorize(PAData.SURVIVAL.getPrefix() + ChatColor.DARK_GRAY + " Tienes el pvp desactivado."));
                        return;
                    }

                    if(Files.user.getBoolean("Users." + pl.getName() + ".pvp") == false){
                        p.sendMessage(Utils.colorize(PAData.SURVIVAL.getPrefix() + ChatColor.GOLD + pl.getName() + ChatColor.DARK_GRAY + " tiene el pvp desactivado."));
                        return;
                    }
                }
            }
        }

        if(damager instanceof Player){
            if(en instanceof  Player){
                Player p = (Player)damager;
                Player pl = (Player)en;

                if (p.hasMetadata("NPC") || pl.hasMetadata("NPC")) return;

                if(Files.user.getBoolean("Users." + p.getName() + ".pvp") == true && Files.user.getBoolean("Users." + pl.getName() + ".pvp") == true){

                    if(p.getGameMode() == GameMode.CREATIVE){
                        p.setGameMode(GameMode.SURVIVAL);
                    }

                    if(manager.isInPvP(pl)) return;
                    if(manager.isInPvP(p)) return;
                    if(manager.isNewbie(pl)) return;
                    if(manager.isNewbie(p)) return;


                    manager.addPvp(p);
                    manager.addPvp(pl);

                    p.sendMessage(Utils.colorize(PAData.SURVIVAL.getPrefix() + ChatColor.DARK_RED + " Has entrado en pelea con " + ChatColor.DARK_GRAY + pl.getName()));
                    pl.sendMessage(Utils.colorize(PAData.SURVIVAL.getPrefix() + ChatColor.DARK_RED + " Has entrado en pelea con " + ChatColor.DARK_GRAY + p.getName()));

                }else{

                    e.setCancelled(true);
                    e.setDamage(0D);
                    pl.setFireTicks(0);

                    if(manager.isNewbie(p)){
                        p.sendMessage(Utils.colorize(PAData.SURVIVAL.getPrefix() + ChatColor.DARK_GRAY + " Tienes la protección de novato activada, para desactivarla haz /pvp disable."));
                        return;
                    }

                    if(manager.isNewbie(pl)){
                        p.sendMessage(Utils.colorize(PAData.SURVIVAL.getPrefix() + ChatColor.GOLD + pl.getName() + ChatColor.DARK_GRAY + " tiene la protección de novato activada."));
                        return;
                    }

                    if(Files.user.getBoolean("Users." + p.getName() + ".pvp") == false){
                        p.sendMessage(Utils.colorize(PAData.SURVIVAL.getPrefix() + ChatColor.DARK_GRAY + " Tienes el pvp desactivado."));
                        return;
                    }

                    if(Files.user.getBoolean("Users." + pl.getName() + ".pvp") == false){
                        p.sendMessage(Utils.colorize(PAData.SURVIVAL.getPrefix() + ChatColor.GOLD + pl.getName() + ChatColor.DARK_GRAY + " tiene el pvp desactivado."));
                        return;
                    }
                }
            }
        }
    }
}
