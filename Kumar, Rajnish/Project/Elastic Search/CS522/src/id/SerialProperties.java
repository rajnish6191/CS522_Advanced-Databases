package id;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InvalidObjectException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.Reader;
import java.io.Serializable;
import java.io.Writer;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Enumeration;
import java.util.InvalidPropertiesFormatException;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.TreeMap;
import java.util.Vector;

public final class SerialProperties implements Serializable {
	private static final long serialVersionUID = 1L;
	private transient Map<String, String> properties;
	private transient boolean suppressDate;

	/**
	 * Creates a new instance that will keep the properties in the order they
	 * have been added. Other than the ordering of the keys, this instance
	 * behaves like an instance of the {@link Properties} class.
	 */
	public SerialProperties() {
		this(new LinkedHashMap<String, String>(), false);
	}

	private SerialProperties(Map<String, String> properties,
			boolean suppressDate) {
		this.properties = properties;
		this.suppressDate = suppressDate;
	}

	/**
	 * See {@link Properties#getProperty(String)}.
	 */
	public String getProperty(String key) {
		return properties.get(key);
	}

	/**
	 * See {@link Properties#getProperty(String, String)}.
	 */
	public String getProperty(String key, String defaultValue) {
		String value = properties.get(key);
		return (value == null) ? defaultValue : value;
	}

	/**
	 * See {@link Properties#setProperty(String, String)}.
	 */
	public String setProperty(String key, String value) {
		return properties.put(key, value);
	}

	/**
	 * Removes the property with the specified key, if it is present. Returns
	 * the value of the property, or <tt>null</tt> if there was no property with
	 * the specified key.
	 * 
	 * @param key
	 *            the key of the property to remove
	 * @return the previous value of the property, or <tt>null</tt> if there was
	 *         no property with the specified key
	 */
	public String removeProperty(String key) {
		return properties.remove(key);
	}

	/**
	 * Returns <tt>true</tt> if there is a property with the specified key.
	 * 
	 * @param key
	 *            the key whose presence is to be tested
	 */
	public boolean containsProperty(String key) {
		return properties.containsKey(key);
	}

	/**
	 * See {@link Properties#size()}.
	 */
	public int size() {
		return properties.size();
	}

	/**
	 * See {@link Properties#isEmpty()}.
	 */
	public boolean isEmpty() {
		return properties.isEmpty();
	}

	/**
	 * See {@link Properties#propertyNames()}.
	 */
	public Enumeration<String> propertyNames() {
		return new Vector<String>(properties.keySet()).elements();
	}

	/**
	 * See {@link Properties#stringPropertyNames()}.
	 */
	public Set<String> stringPropertyNames() {
		return new LinkedHashSet<String>(properties.keySet());
	}

	/**
	 * See {@link Properties#entrySet()}.
	 */
	public Set<Map.Entry<String, String>> entrySet() {
		return new LinkedHashSet<Map.Entry<String, String>>(
				properties.entrySet());
	}

	/**
	 * See {@link Properties#load(InputStream)}.
	 */
	public void load(InputStream stream) throws IOException {
		CustomProperties customProperties = new CustomProperties(
				this.properties);
		customProperties.load(stream);
	}

	/**
	 * See {@link Properties#load(Reader)}.
	 */
	public void load(Reader reader) throws IOException {
		CustomProperties customProperties = new CustomProperties(
				this.properties);
		customProperties.load(reader);
	}

	/**
	 * See {@link Properties#loadFromXML(InputStream)}.
	 */
	public void loadFromXML(InputStream stream) throws IOException,
			InvalidPropertiesFormatException {
		CustomProperties customProperties = new CustomProperties(
				this.properties);
		customProperties.loadFromXML(stream);
	}

	/**
	 * See {@link Properties#store(OutputStream, String)}.
	 */
	@SuppressWarnings("resource")
	public void store(OutputStream stream, String comments) throws IOException {
		CustomProperties customProperties = new CustomProperties(
				this.properties);
		if (suppressDate) {
			customProperties.store(new DateSuppressingPropertiesBufferedWriter(	new OutputStreamWriter(stream, "8859_1")), comments);
		} else {
			customProperties.store(stream, comments);
		}
	}

	/**
	 * See {@link Properties#store(Writer, String)}.
	 */
	@SuppressWarnings("resource")
	public void store(Writer writer, String comments) throws IOException {
		CustomProperties customProperties = new CustomProperties(
				this.properties);
		if (suppressDate) {
			customProperties.store(new DateSuppressingPropertiesBufferedWriter(	writer), comments);
		} else {
			customProperties.store(writer, comments);
		}
	}

	/**
	 * See {@link Properties#storeToXML(OutputStream, String)}.
	 */
	public void storeToXML(OutputStream stream, String comment)
			throws IOException {
		CustomProperties customProperties = new CustomProperties(
				this.properties);
		customProperties.storeToXML(stream, comment);
	}

	/**
	 * See {@link Properties#storeToXML(OutputStream, String, String)}.
	 */
	public void storeToXML(OutputStream stream, String comment, String encoding)
			throws IOException {
		CustomProperties customProperties = new CustomProperties(
				this.properties);
		customProperties.storeToXML(stream, comment, encoding);
	}

	/**
	 * See {@link Properties#list(PrintStream)}.
	 */
	public void list(PrintStream stream) {
		CustomProperties customProperties = new CustomProperties(
				this.properties);
		customProperties.list(stream);
	}

	/**
	 * See {@link Properties#list(PrintWriter)}.
	 */
	public void list(PrintWriter writer) {
		CustomProperties customProperties = new CustomProperties(
				this.properties);
		customProperties.list(writer);
	}

	/**
	 * Convert this instance to a {@link Properties} instance.
	 * 
	 * @return the {@link Properties} instance
	 */
	public Properties toJdkProperties() {
		Properties jdkProperties = new Properties();
		for (Map.Entry<String, String> entry : this.entrySet()) {
			jdkProperties.put(entry.getKey(), entry.getValue());
		}
		return jdkProperties;
	}

	@Override
	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (other == null || getClass() != other.getClass()) {
			return false;
		}
		SerialProperties that = (SerialProperties) other;
		return Arrays.equals(properties.entrySet().toArray(), that.properties
				.entrySet().toArray());
	}

	@Override
	public int hashCode() {
		return Arrays.hashCode(properties.entrySet().toArray());
	}

	private void writeObject(ObjectOutputStream stream) throws IOException {
		stream.defaultWriteObject();
		stream.writeObject(properties);
		stream.writeBoolean(suppressDate);
	}

	@SuppressWarnings("unchecked")
	private void readObject(ObjectInputStream stream) throws IOException,
			ClassNotFoundException {
		stream.defaultReadObject();
		properties = (Map<String, String>) stream.readObject();
		suppressDate = stream.readBoolean();
	}

	@SuppressWarnings("unused")
	private void readObjectNoData() throws InvalidObjectException {
		throw new InvalidObjectException("Stream data required");
	}

	/**
	 * See {@link Properties#toString()}.
	 */
	@Override
	public String toString() {
		return properties.toString();
	}

	/**
	 * Creates a new instance that will have both the same property entries and
	 * the same behavior as the given source.
	 * <p/>
	 * Note that the source instance and the copy instance will share the same
	 * comparator instance if a custom ordering had been configured on the
	 * source.
	 * 
	 * @param source
	 *            the source to copy from
	 * @return the copy
	 */
	public static SerialProperties copyOf(SerialProperties source) {
		// create a copy that has the same behaviour
		OrderedPropertiesBuilder builder = new OrderedPropertiesBuilder();
		builder.withSuppressDateInComment(source.suppressDate);
		if (source.properties instanceof TreeMap) {
			builder.withOrdering(((TreeMap<String, String>) source.properties)
					.comparator());
		}
		SerialProperties result = builder.build();
		// copy the properties from the source to the target
		for (Map.Entry<String, String> entry : source.entrySet()) {
			result.setProperty(entry.getKey(), entry.getValue());
		}
		return result;
	}

	/**
	 * Builder for {@link SerialProperties} instances.
	 */
	public static final class OrderedPropertiesBuilder {
		private Comparator<? super String> comparator;
		private boolean suppressDate;

		/**
		 * Use a custom ordering of the keys.
		 * 
		 * @param comparator
		 *            the ordering to apply on the keys
		 * @return the builder
		 */
		public OrderedPropertiesBuilder withOrdering(
				Comparator<? super String> comparator) {
			this.comparator = comparator;
			return this;
		}

		/**
		 * Suppress the comment that contains the current date when storing the
		 * properties.
		 * 
		 * @param suppressDate
		 *            whether to suppress the comment that contains the current
		 *            date
		 * @return the builder
		 */
		public OrderedPropertiesBuilder withSuppressDateInComment(
				boolean suppressDate) {
			this.suppressDate = suppressDate;
			return this;
		}

		/**
		 * Builds a new {@link SerialProperties} instance.
		 * 
		 * @return the new instance
		 */
		public SerialProperties build() {
			Map<String, String> properties = (this.comparator != null) ? new TreeMap<String, String>(
					comparator) : new LinkedHashMap<String, String>();
			return new SerialProperties(properties, suppressDate);
		}
	}

	/**
	 * Custom {@link Properties} that delegates reading, writing, and
	 * enumerating properties to the backing {@link SerialProperties} instance's
	 * properties.
	 */
	private static final class CustomProperties extends Properties {
		/**
	 * 
	 */
		private static final long serialVersionUID = 1L;
		private final Map<String, String> targetProperties;

		private CustomProperties(Map<String, String> targetProperties) {
			this.targetProperties = targetProperties;
		}

		@Override
		public Object get(Object key) {
			return targetProperties.get(key);
		}

		@Override
		public Object put(Object key, Object value) {
			return targetProperties.put((String) key, (String) value);
		}

		@Override
		public String getProperty(String key) {
			return targetProperties.get(key);
		}

		@Override
		public Enumeration<Object> keys() {
			return new Vector<Object>(targetProperties.keySet()).elements();
		}

		@Override
		public Set<Object> keySet() {
			return new LinkedHashSet<Object>(targetProperties.keySet());
		}
	}

	/**
	 * Custom {@link BufferedWriter} for storing properties that will write all
	 * leading lines of comments except the last comment line. Using the JDK
	 * Properties class to store properties, the last comment line always
	 * contains the current date which is what we want to filter out.
	 */
	private static final class DateSuppressingPropertiesBufferedWriter extends
			BufferedWriter {
		private final String LINE_SEPARATOR = System
				.getProperty("line.separator");
		private StringBuilder currentComment;
		private String previousComment;

		private DateSuppressingPropertiesBufferedWriter(Writer out) {
			super(out);
		}

		@Override
		public void write(String string) throws IOException {
			if (currentComment != null) {
				currentComment.append(string);
				if (string.endsWith(LINE_SEPARATOR)) {
					if (previousComment != null) {
						super.write(previousComment);
					}
					previousComment = currentComment.toString();
					currentComment = null;
				}
			} else if (string.startsWith("#")) {
				currentComment = new StringBuilder(string);
			} else {
				super.write(string);
			}
		}
	}
}