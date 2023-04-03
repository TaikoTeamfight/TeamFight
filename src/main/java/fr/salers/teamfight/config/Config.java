package fr.salers.teamfight.config;

import fr.salers.teamfight.TFight;
import fr.salers.teamfight.utilities.CC;
import lombok.Getter;

@Getter
public enum Config {
    PREFIX("teamfight.prefix", "&a&lTAIKO &r&7> "),
    BLOCK_BREAK("teamfight.block-break-enabled", true);

    private final String path;
    private final Object value;

    Config(String path, Object value) {
        this.path = path;

        if (TFight.INSTANCE.getPlugin().getConfig().contains(path)) {
            this.value = TFight.INSTANCE.getPlugin().getConfig().get(path);
        } else {
            TFight.INSTANCE.getPlugin().getConfig().set(path, value);
            TFight.INSTANCE.getPlugin().saveConfig();

            this.value = value;
        }
    }

    public String translate() {
        return CC.translate((String) value);
    }
}
