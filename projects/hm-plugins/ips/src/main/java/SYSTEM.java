import org.homemotion.Mode;
import org.homemotion.building.Building;
import org.homemotion.category.Category;
import org.homemotion.category.CategoryManager;
import org.homemotion.devices.Device;
import org.homemotion.devices.ModeManager;
import org.homemotion.di.Registry;

public final class SYSTEM {

	public static Category getCategory(Building building, String path) {
		CategoryManager catMan = Registry.getInstance(CategoryManager.class);
		return catMan.getByPath(building, path);
	}

	public static Device getDevice(Building building, String category, String name) {
		Category cat = getCategory(building, category);
		return cat.getDevice(name);
	}
}
