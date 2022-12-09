package me.vickrum.alts.extra;

public class SkullWrapper {
	private String owner;

	private String texture;

	public static SkullWrapperBuilder builder() {
		return new SkullWrapperBuilder();
	}

	public static class SkullWrapperBuilder {
		private String owner;

		private String texture;

		public SkullWrapperBuilder owner(String owner) {
			this.owner = owner;
			return this;
		}

		public SkullWrapperBuilder texture(String texture) {
			this.texture = texture;
			return this;
		}

		public SkullWrapper build() {
			return new SkullWrapper(this.owner, this.texture);
		}

		public String toString() {
			return "SkullWrapper.SkullWrapperBuilder(owner=" + this.owner + ", texture=" + this.texture + ")";
		}
	}

	public SkullWrapper() {
	}

	public SkullWrapper(String owner, String texture) {
		this.owner = owner;
		this.texture = texture;
	}

	public String getOwner() {
		return this.owner;
	}

	public String getTexture() {
		return this.texture;
	}

	public void setOwner(String owner) {
		this.owner = owner;
	}

	public void setTexture(String texture) {
		this.texture = texture;
	}
}