package me.vickrum.alts.extra;

import com.google.common.collect.Iterables;
import com.massivecraft.massivecore.Couple;
import com.massivecraft.massivecore.collections.MassiveList;
import com.massivecraft.massivecore.particleeffect.ReflectionUtils;
import com.massivecraft.massivecore.util.ReflectionUtil;

import org.bukkit.inventory.meta.SkullMeta;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@SuppressWarnings("unchecked")
public class NmsSkullTexture18R1P extends NmsSkullTexture {

	// -------------------------------------------- //
	// INSTANCE & CONSTRUCT
	// -------------------------------------------- //

	private static final NmsSkullTexture18R1P i = new NmsSkullTexture18R1P();

	public static NmsSkullTexture18R1P get() {
		return i;
	}
	// -------------------------------------------- //
	// FIELDS
	// -------------------------------------------- //

	// org.bukkit.craftbukkit.inventory.CraftMetaSkull
	public Class<?> classCraftMetaSkull;
	// org.bukkit.craftbukkit.inventory.CraftMetaSkull#profile
	public Field fieldCraftMetaSkullProfile;

	// 17R4: net.minecraft.util.com.mojang.authlib.GameProfile
	// 18R1P: com.mojang.authlib.GameProfile
	public Class<?> classGameProfile;
	// ...#id
	public Field fieldGameProfileId;
	// ...#name
	public Field fieldGameProfileName;
	// #properties
	public Field fieldGameProfilePropertyMap;
	// ...(UUID id, String name);
	public Constructor<?> constructorGameProfile;

	public Class<?> classPropertyMap;
	public Method methodPropertyMapEntries;
	public Constructor<?> constructorPropertyMap;

	// com.mojang.authlib.properties
	public Class<?> classProperty;
	// name, value, signature
	// (string, string, string)
	public Constructor<?> constructorProperty;

	// #name
	public Field fieldPropertyName;
	// #value
	public Field fieldPropertyValue;
	// #signature
	public Field fieldPropertySignature;

	public Method methodPropertyMapClear;
	public Method methodPropertyMapPut;

	// -------------------------------------------- //
	// SETUP
	// -------------------------------------------- //

	@Override
	public void setup() throws Throwable {
		this.classCraftMetaSkull = ReflectionUtils.PackageType.CRAFTBUKKIT_INVENTORY.getClass("CraftMetaSkull");
		this.fieldCraftMetaSkullProfile = ReflectionUtil.getField(this.classCraftMetaSkull, "profile");

		this.classGameProfile = Class.forName("com.mojang.authlib.GameProfile");
		this.fieldGameProfileId = ReflectionUtil.getField(this.classGameProfile, "id");
		this.fieldGameProfileName = ReflectionUtil.getField(this.classGameProfile, "name");
		this.fieldGameProfilePropertyMap = ReflectionUtil.getField(this.classGameProfile, "properties");
		this.classPropertyMap = this.fieldGameProfilePropertyMap.getType();
		this.constructorPropertyMap = ReflectionUtil.getConstructor(this.classPropertyMap);

		Class<?> classDeclaringPropertyMapEntries = ReflectionUtil.getSuperclassDeclaringMethod(this.classPropertyMap,
				true, "entries");
		this.methodPropertyMapEntries = ReflectionUtil.getMethod(classDeclaringPropertyMapEntries, "entries");

		this.constructorGameProfile = ReflectionUtil.getConstructor(this.classGameProfile, UUID.class, String.class);
		this.classProperty = Class.forName("com.mojang.authlib.properties.Property");
		this.constructorProperty = ReflectionUtil.getConstructor(this.classProperty, String.class, String.class,
				String.class);
		this.fieldPropertyName = ReflectionUtil.getField(this.classProperty, "name");
		this.fieldPropertyValue = ReflectionUtil.getField(this.classProperty, "value");
		this.fieldPropertySignature = ReflectionUtil.getField(this.classProperty, "signature");

		Class<?> classDeclaringPropertyMapClear = ReflectionUtil.getSuperclassDeclaringMethod(this.classPropertyMap,
				true, "clear");
		this.methodPropertyMapClear = ReflectionUtil.getMethod(classDeclaringPropertyMapClear, "clear");

		Class<?> classDeclaringPropertyMapPut = ReflectionUtil.getSuperclassDeclaringMethod(this.classPropertyMap, true,
				"put");
		Method[] methodsDeclaredInPut = classDeclaringPropertyMapPut.getDeclaredMethods();
		for (Method method : methodsDeclaredInPut) {
			if (!method.getName().equals("put"))
				continue;

			this.methodPropertyMapPut = method;
			break;
		}
	}

	// -------------------------------------------- //
	// RAW
	// -------------------------------------------- //

	@Override
	public String getTexture(SkullMeta skullMeta) {
		Object gameProfile = getGameProfile(skullMeta);

		if (gameProfile == null)
			return null;

		return getGameProfileTexture(gameProfile);
	}

	@Override
	public void set(SkullMeta meta, String name, UUID id, String texture) {
		Object gameProfile = createGameProfile(id, name);

		setGameProfileTexture(gameProfile, texture);
		setGameProfile(meta, gameProfile);
	}

	@Override
	public void set(SkullMeta skullMeta, String name, UUID id) {
		skullMeta.setOwner(name);
	}

	// -------------------------------------------- //
	// GAMEPROFILE
	// -------------------------------------------- //

	@Override
	public <T> T createGameProfile(UUID id, String name) {
		return ReflectionUtil.invokeConstructor(this.constructorGameProfile, id, name);
	}

	@Override
	public <T> T getGameProfile(SkullMeta meta) {
		return ReflectionUtil.getField(this.fieldCraftMetaSkullProfile, meta);
	}

	@Override
	public void setGameProfile(SkullMeta meta, Object gameProfile) {
		ReflectionUtil.setField(this.fieldCraftMetaSkullProfile, meta, gameProfile);
	}

	// -------------------------------------------- //
	// GAMEPROFILE > GET
	// -------------------------------------------- //

	@Override
	public String getGameProfileName(Object gameProfile) {
		return ReflectionUtil.getField(this.fieldGameProfileName, gameProfile);
	}

	@Override
	public UUID getGameProfileId(Object gameProfile) {
		return ReflectionUtil.getField(this.fieldGameProfileId, gameProfile);
	}

	protected String getGameProfileTexture(Object gameProfile) {
		Object propertyMap = getPropertyMap(gameProfile);

		if (propertyMap == null)
			return null;

		Object property = Iterables.getFirst(
				getGameProfileProperties(propertyMap).stream()
						.filter(entry -> entry.getKey().equalsIgnoreCase("textures")).collect(Collectors.toList()),
				null);

		if (property == null)
			return null;
		if (!(property instanceof Couple<?, ?>))
			return null;

		Couple<String, ContainerGameProfileProperty> couple = (Couple<String, ContainerGameProfileProperty>) property;
		ContainerGameProfileProperty container = couple.getSecond();

		return container.value;
	}

	// -------------------------------------------- //
	// GAMEPROFILE > SET
	// -------------------------------------------- //

	protected void setGameProfileName(Object gameProfile, String name) {
		ReflectionUtil.setField(this.fieldGameProfileName, gameProfile, name);
	}

	protected void setGameProfileId(Object gameProfile, UUID id) {
		ReflectionUtil.setField(this.fieldGameProfileId, gameProfile, id);
	}

	protected void setGameProfileTexture(Object gameProfile, String texture) {
		Object propertyMap = getPropertyMap(gameProfile);

		if (propertyMap == null)
			return;

		ContainerGameProfileProperty containerGameProfileProperty = new ContainerGameProfileProperty();
		containerGameProfileProperty.name = "texture";
		containerGameProfileProperty.value = texture;

		Object newPropertyObject = containerToProperty(containerGameProfileProperty);
		putPropertyInMap(propertyMap, "textures", newPropertyObject);
	}

	// -------------------------------------------- //
	// GAMEPROFILE > PROPERTIES
	// -------------------------------------------- //

	@Override
	public <T> T getPropertyMap(Object profile) {
		if (profile == null)
			return (T) this.createPropertyMap();
		return ReflectionUtil.getField(this.fieldGameProfilePropertyMap, profile);
	}

	@Override
	public void setPropertyMap(Object profile, Object propertyMap) {
		ReflectionUtil.setField(this.fieldGameProfilePropertyMap, profile, propertyMap);
	}

	// -------------------------------------------- //
	// PROPERTYMAP >
	// -------------------------------------------- //

	private Collection<Map.Entry<String, ?>> getPropertiesFromPropertyMap(Object propertyMap) {
		return ReflectionUtil.invokeMethod(this.methodPropertyMapEntries, propertyMap);
	}

	// -------------------------------------------- //
	// PROPERTY > GET
	// -------------------------------------------- //

	@Override
	public Collection<Map.Entry<String, ContainerGameProfileProperty>> getGameProfileProperties(Object propertyMap) {
		Collection<Map.Entry<String, ?>> inner = this.getPropertiesFromPropertyMap(propertyMap);
		if (inner.isEmpty())
			return Collections.emptyList();

		Collection<Map.Entry<String, ContainerGameProfileProperty>> ret = new MassiveList<>();
		for (Map.Entry<String, ?> entry : inner) {
			Object propertyObject = entry.getValue();
			ContainerGameProfileProperty propertyTriple = unsafePropertyToContainer(propertyObject);

			ret.add(Couple.valueOf(entry.getKey(), propertyTriple));
		}
		return ret;
	}

	private ContainerGameProfileProperty unsafePropertyToContainer(Object property) {
		String name = ReflectionUtil.getField(this.fieldPropertyName, property);
		String value = ReflectionUtil.getField(this.fieldPropertyValue, property);
		String signature = ReflectionUtil.getField(this.fieldPropertySignature, property);
		ContainerGameProfileProperty ret = new ContainerGameProfileProperty();
		ret.name = name;
		ret.value = value;
		ret.signature = signature;
		return ret;
	}

	// -------------------------------------------- //
	// PROPERTYMAP > SET
	// -------------------------------------------- //

	@Override
	public Object createPropertyMap() {
		return ReflectionUtil.invokeConstructor(this.constructorPropertyMap);
	}

	@Override
	public void setGameProfileProperties(Object propertyMap,
			Collection<Map.Entry<String, ContainerGameProfileProperty>> properties) {
		clearPropertyMap(propertyMap);
		for (Map.Entry<String, ContainerGameProfileProperty> entry : properties) {
			// Create the property objects
			Object newPropertyObject = containerToProperty(entry.getValue());

			// Add the property objects to propertyMap
			putPropertyInMap(propertyMap, entry.getKey(), newPropertyObject);
		}
	}

	public void clearPropertyMap(Object propertyMap) {
		ReflectionUtil.invokeMethod(this.methodPropertyMapClear, propertyMap);
	}

	public void putPropertyInMap(Object propertyMap, String key, Object value) {
		ReflectionUtil.invokeMethod(this.methodPropertyMapPut, propertyMap, key, value);
	}

	private <T> T containerToProperty(ContainerGameProfileProperty prop) {
		T ret = ReflectionUtil.invokeConstructor(this.constructorProperty, null, null, null);
		ReflectionUtil.setField(this.fieldPropertyName, ret, prop.name);
		ReflectionUtil.setField(this.fieldPropertyValue, ret, prop.value);
		ReflectionUtil.setField(this.fieldPropertySignature, ret, prop.signature);
		return ret;
	}

}