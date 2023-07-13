/**
 * Autogenerated by Avro
 *
 * DO NOT EDIT DIRECTLY
 */
package schemas.messaging.avro.events.epics.modified;

import org.apache.avro.generic.GenericArray;
import org.apache.avro.specific.SpecificData;
import org.apache.avro.util.Utf8;
import org.apache.avro.message.BinaryMessageEncoder;
import org.apache.avro.message.BinaryMessageDecoder;
import org.apache.avro.message.SchemaStore;

@org.apache.avro.specific.AvroGenerated
public class EpicModified extends org.apache.avro.specific.SpecificRecordBase implements org.apache.avro.specific.SpecificRecord {
  private static final long serialVersionUID = -5970018764218010876L;
  public static final org.apache.avro.Schema SCHEMA$ = new org.apache.avro.Schema.Parser().parse("{\"type\":\"record\",\"name\":\"EpicModified\",\"namespace\":\"schemas.messaging.avro.events.epics.modified\",\"fields\":[{\"name\":\"id\",\"type\":{\"type\":\"string\",\"avro.java.string\":\"String\",\"logicalType\":\"UUID\"}},{\"name\":\"name\",\"type\":{\"type\":\"string\",\"avro.java.string\":\"String\"}},{\"name\":\"assignee\",\"type\":{\"type\":\"string\",\"avro.java.string\":\"String\"}},{\"name\":\"isModified\",\"type\":[\"boolean\"],\"default\":false}]}");
  public static org.apache.avro.Schema getClassSchema() { return SCHEMA$; }

  private static SpecificData MODEL$ = new SpecificData();

  private static final BinaryMessageEncoder<EpicModified> ENCODER =
      new BinaryMessageEncoder<EpicModified>(MODEL$, SCHEMA$);

  private static final BinaryMessageDecoder<EpicModified> DECODER =
      new BinaryMessageDecoder<EpicModified>(MODEL$, SCHEMA$);

  /**
   * Return the BinaryMessageEncoder instance used by this class.
   * @return the message encoder used by this class
   */
  public static BinaryMessageEncoder<EpicModified> getEncoder() {
    return ENCODER;
  }

  /**
   * Return the BinaryMessageDecoder instance used by this class.
   * @return the message decoder used by this class
   */
  public static BinaryMessageDecoder<EpicModified> getDecoder() {
    return DECODER;
  }

  /**
   * Create a new BinaryMessageDecoder instance for this class that uses the specified {@link SchemaStore}.
   * @param resolver a {@link SchemaStore} used to find schemas by fingerprint
   * @return a BinaryMessageDecoder instance for this class backed by the given SchemaStore
   */
  public static BinaryMessageDecoder<EpicModified> createDecoder(SchemaStore resolver) {
    return new BinaryMessageDecoder<EpicModified>(MODEL$, SCHEMA$, resolver);
  }

  /**
   * Serializes this EpicModified to a ByteBuffer.
   * @return a buffer holding the serialized data for this instance
   * @throws java.io.IOException if this instance could not be serialized
   */
  public java.nio.ByteBuffer toByteBuffer() throws java.io.IOException {
    return ENCODER.encode(this);
  }

  /**
   * Deserializes a EpicModified from a ByteBuffer.
   * @param b a byte buffer holding serialized data for an instance of this class
   * @return a EpicModified instance decoded from the given buffer
   * @throws java.io.IOException if the given bytes could not be deserialized into an instance of this class
   */
  public static EpicModified fromByteBuffer(
      java.nio.ByteBuffer b) throws java.io.IOException {
    return DECODER.decode(b);
  }

  @Deprecated public java.lang.String id;
  @Deprecated public java.lang.String name;
  @Deprecated public java.lang.String assignee;
  @Deprecated public java.lang.Object isModified;

  /**
   * Default constructor.  Note that this does not initialize fields
   * to their default values from the schema.  If that is desired then
   * one should use <code>newBuilder()</code>.
   */
  public EpicModified() {}

  /**
   * All-args constructor.
   * @param id The new value for id
   * @param name The new value for name
   * @param assignee The new value for assignee
   * @param isModified The new value for isModified
   */
  public EpicModified(java.lang.String id, java.lang.String name, java.lang.String assignee, java.lang.Object isModified) {
    this.id = id;
    this.name = name;
    this.assignee = assignee;
    this.isModified = isModified;
  }

  public org.apache.avro.specific.SpecificData getSpecificData() { return MODEL$; }
  public org.apache.avro.Schema getSchema() { return SCHEMA$; }
  // Used by DatumWriter.  Applications should not call.
  public java.lang.Object get(int field$) {
    switch (field$) {
    case 0: return id;
    case 1: return name;
    case 2: return assignee;
    case 3: return isModified;
    default: throw new IndexOutOfBoundsException("Invalid index: " + field$);
    }
  }

  // Used by DatumReader.  Applications should not call.
  @SuppressWarnings(value="unchecked")
  public void put(int field$, java.lang.Object value$) {
    switch (field$) {
    case 0: id = value$ != null ? value$.toString() : null; break;
    case 1: name = value$ != null ? value$.toString() : null; break;
    case 2: assignee = value$ != null ? value$.toString() : null; break;
    case 3: isModified = value$; break;
    default: throw new IndexOutOfBoundsException("Invalid index: " + field$);
    }
  }

  /**
   * Gets the value of the 'id' field.
   * @return The value of the 'id' field.
   */
  public java.lang.String getId() {
    return id;
  }


  /**
   * Sets the value of the 'id' field.
   * @param value the value to set.
   */
  public void setId(java.lang.String value) {
    this.id = value;
  }

  /**
   * Gets the value of the 'name' field.
   * @return The value of the 'name' field.
   */
  public java.lang.String getName() {
    return name;
  }


  /**
   * Sets the value of the 'name' field.
   * @param value the value to set.
   */
  public void setName(java.lang.String value) {
    this.name = value;
  }

  /**
   * Gets the value of the 'assignee' field.
   * @return The value of the 'assignee' field.
   */
  public java.lang.String getAssignee() {
    return assignee;
  }


  /**
   * Sets the value of the 'assignee' field.
   * @param value the value to set.
   */
  public void setAssignee(java.lang.String value) {
    this.assignee = value;
  }

  /**
   * Gets the value of the 'isModified' field.
   * @return The value of the 'isModified' field.
   */
  public java.lang.Object getIsModified() {
    return isModified;
  }


  /**
   * Sets the value of the 'isModified' field.
   * @param value the value to set.
   */
  public void setIsModified(java.lang.Object value) {
    this.isModified = value;
  }

  /**
   * Creates a new EpicModified RecordBuilder.
   * @return A new EpicModified RecordBuilder
   */
  public static schemas.messaging.avro.events.epics.modified.EpicModified.Builder newBuilder() {
    return new schemas.messaging.avro.events.epics.modified.EpicModified.Builder();
  }

  /**
   * Creates a new EpicModified RecordBuilder by copying an existing Builder.
   * @param other The existing builder to copy.
   * @return A new EpicModified RecordBuilder
   */
  public static schemas.messaging.avro.events.epics.modified.EpicModified.Builder newBuilder(schemas.messaging.avro.events.epics.modified.EpicModified.Builder other) {
    if (other == null) {
      return new schemas.messaging.avro.events.epics.modified.EpicModified.Builder();
    } else {
      return new schemas.messaging.avro.events.epics.modified.EpicModified.Builder(other);
    }
  }

  /**
   * Creates a new EpicModified RecordBuilder by copying an existing EpicModified instance.
   * @param other The existing instance to copy.
   * @return A new EpicModified RecordBuilder
   */
  public static schemas.messaging.avro.events.epics.modified.EpicModified.Builder newBuilder(schemas.messaging.avro.events.epics.modified.EpicModified other) {
    if (other == null) {
      return new schemas.messaging.avro.events.epics.modified.EpicModified.Builder();
    } else {
      return new schemas.messaging.avro.events.epics.modified.EpicModified.Builder(other);
    }
  }

  /**
   * RecordBuilder for EpicModified instances.
   */
  @org.apache.avro.specific.AvroGenerated
  public static class Builder extends org.apache.avro.specific.SpecificRecordBuilderBase<EpicModified>
    implements org.apache.avro.data.RecordBuilder<EpicModified> {

    private java.lang.String id;
    private java.lang.String name;
    private java.lang.String assignee;
    private java.lang.Object isModified;

    /** Creates a new Builder */
    private Builder() {
      super(SCHEMA$);
    }

    /**
     * Creates a Builder by copying an existing Builder.
     * @param other The existing Builder to copy.
     */
    private Builder(schemas.messaging.avro.events.epics.modified.EpicModified.Builder other) {
      super(other);
      if (isValidValue(fields()[0], other.id)) {
        this.id = data().deepCopy(fields()[0].schema(), other.id);
        fieldSetFlags()[0] = other.fieldSetFlags()[0];
      }
      if (isValidValue(fields()[1], other.name)) {
        this.name = data().deepCopy(fields()[1].schema(), other.name);
        fieldSetFlags()[1] = other.fieldSetFlags()[1];
      }
      if (isValidValue(fields()[2], other.assignee)) {
        this.assignee = data().deepCopy(fields()[2].schema(), other.assignee);
        fieldSetFlags()[2] = other.fieldSetFlags()[2];
      }
      if (isValidValue(fields()[3], other.isModified)) {
        this.isModified = data().deepCopy(fields()[3].schema(), other.isModified);
        fieldSetFlags()[3] = other.fieldSetFlags()[3];
      }
    }

    /**
     * Creates a Builder by copying an existing EpicModified instance
     * @param other The existing instance to copy.
     */
    private Builder(schemas.messaging.avro.events.epics.modified.EpicModified other) {
      super(SCHEMA$);
      if (isValidValue(fields()[0], other.id)) {
        this.id = data().deepCopy(fields()[0].schema(), other.id);
        fieldSetFlags()[0] = true;
      }
      if (isValidValue(fields()[1], other.name)) {
        this.name = data().deepCopy(fields()[1].schema(), other.name);
        fieldSetFlags()[1] = true;
      }
      if (isValidValue(fields()[2], other.assignee)) {
        this.assignee = data().deepCopy(fields()[2].schema(), other.assignee);
        fieldSetFlags()[2] = true;
      }
      if (isValidValue(fields()[3], other.isModified)) {
        this.isModified = data().deepCopy(fields()[3].schema(), other.isModified);
        fieldSetFlags()[3] = true;
      }
    }

    /**
      * Gets the value of the 'id' field.
      * @return The value.
      */
    public java.lang.String getId() {
      return id;
    }


    /**
      * Sets the value of the 'id' field.
      * @param value The value of 'id'.
      * @return This builder.
      */
    public schemas.messaging.avro.events.epics.modified.EpicModified.Builder setId(java.lang.String value) {
      validate(fields()[0], value);
      this.id = value;
      fieldSetFlags()[0] = true;
      return this;
    }

    /**
      * Checks whether the 'id' field has been set.
      * @return True if the 'id' field has been set, false otherwise.
      */
    public boolean hasId() {
      return fieldSetFlags()[0];
    }


    /**
      * Clears the value of the 'id' field.
      * @return This builder.
      */
    public schemas.messaging.avro.events.epics.modified.EpicModified.Builder clearId() {
      id = null;
      fieldSetFlags()[0] = false;
      return this;
    }

    /**
      * Gets the value of the 'name' field.
      * @return The value.
      */
    public java.lang.String getName() {
      return name;
    }


    /**
      * Sets the value of the 'name' field.
      * @param value The value of 'name'.
      * @return This builder.
      */
    public schemas.messaging.avro.events.epics.modified.EpicModified.Builder setName(java.lang.String value) {
      validate(fields()[1], value);
      this.name = value;
      fieldSetFlags()[1] = true;
      return this;
    }

    /**
      * Checks whether the 'name' field has been set.
      * @return True if the 'name' field has been set, false otherwise.
      */
    public boolean hasName() {
      return fieldSetFlags()[1];
    }


    /**
      * Clears the value of the 'name' field.
      * @return This builder.
      */
    public schemas.messaging.avro.events.epics.modified.EpicModified.Builder clearName() {
      name = null;
      fieldSetFlags()[1] = false;
      return this;
    }

    /**
      * Gets the value of the 'assignee' field.
      * @return The value.
      */
    public java.lang.String getAssignee() {
      return assignee;
    }


    /**
      * Sets the value of the 'assignee' field.
      * @param value The value of 'assignee'.
      * @return This builder.
      */
    public schemas.messaging.avro.events.epics.modified.EpicModified.Builder setAssignee(java.lang.String value) {
      validate(fields()[2], value);
      this.assignee = value;
      fieldSetFlags()[2] = true;
      return this;
    }

    /**
      * Checks whether the 'assignee' field has been set.
      * @return True if the 'assignee' field has been set, false otherwise.
      */
    public boolean hasAssignee() {
      return fieldSetFlags()[2];
    }


    /**
      * Clears the value of the 'assignee' field.
      * @return This builder.
      */
    public schemas.messaging.avro.events.epics.modified.EpicModified.Builder clearAssignee() {
      assignee = null;
      fieldSetFlags()[2] = false;
      return this;
    }

    /**
      * Gets the value of the 'isModified' field.
      * @return The value.
      */
    public java.lang.Object getIsModified() {
      return isModified;
    }


    /**
      * Sets the value of the 'isModified' field.
      * @param value The value of 'isModified'.
      * @return This builder.
      */
    public schemas.messaging.avro.events.epics.modified.EpicModified.Builder setIsModified(java.lang.Object value) {
      validate(fields()[3], value);
      this.isModified = value;
      fieldSetFlags()[3] = true;
      return this;
    }

    /**
      * Checks whether the 'isModified' field has been set.
      * @return True if the 'isModified' field has been set, false otherwise.
      */
    public boolean hasIsModified() {
      return fieldSetFlags()[3];
    }


    /**
      * Clears the value of the 'isModified' field.
      * @return This builder.
      */
    public schemas.messaging.avro.events.epics.modified.EpicModified.Builder clearIsModified() {
      isModified = null;
      fieldSetFlags()[3] = false;
      return this;
    }

    @Override
    @SuppressWarnings("unchecked")
    public EpicModified build() {
      try {
        EpicModified record = new EpicModified();
        record.id = fieldSetFlags()[0] ? this.id : (java.lang.String) defaultValue(fields()[0]);
        record.name = fieldSetFlags()[1] ? this.name : (java.lang.String) defaultValue(fields()[1]);
        record.assignee = fieldSetFlags()[2] ? this.assignee : (java.lang.String) defaultValue(fields()[2]);
        record.isModified = fieldSetFlags()[3] ? this.isModified :  defaultValue(fields()[3]);
        return record;
      } catch (org.apache.avro.AvroMissingFieldException e) {
        throw e;
      } catch (java.lang.Exception e) {
        throw new org.apache.avro.AvroRuntimeException(e);
      }
    }
  }

  @SuppressWarnings("unchecked")
  private static final org.apache.avro.io.DatumWriter<EpicModified>
    WRITER$ = (org.apache.avro.io.DatumWriter<EpicModified>)MODEL$.createDatumWriter(SCHEMA$);

  @Override public void writeExternal(java.io.ObjectOutput out)
    throws java.io.IOException {
    WRITER$.write(this, SpecificData.getEncoder(out));
  }

  @SuppressWarnings("unchecked")
  private static final org.apache.avro.io.DatumReader<EpicModified>
    READER$ = (org.apache.avro.io.DatumReader<EpicModified>)MODEL$.createDatumReader(SCHEMA$);

  @Override public void readExternal(java.io.ObjectInput in)
    throws java.io.IOException {
    READER$.read(this, SpecificData.getDecoder(in));
  }

}









