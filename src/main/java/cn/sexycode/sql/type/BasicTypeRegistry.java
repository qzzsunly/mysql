package cn.sexycode.sql.type;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * A registry of {@link BasicType} instances
 *
 */
public class BasicTypeRegistry implements Serializable {
    private static final Logger LOG = LoggerFactory.getLogger(BasicTypeRegistry.class);

    // TODO : analyze these sizing params; unfortunately this seems to be the only way to give a "concurrencyLevel"
    private Map<String, BasicType> registry = new ConcurrentHashMap<String, BasicType>(100, .75f, 1);
    private boolean locked;

    public BasicTypeRegistry() {
        register(BooleanType.INSTANCE);
        register(NumericBooleanType.INSTANCE);
        register(TrueFalseType.INSTANCE);
        register(YesNoType.INSTANCE);

        register(ByteType.INSTANCE);
        register(CharacterType.INSTANCE);
        register(ShortType.INSTANCE);
        register(IntegerType.INSTANCE);
        register(LongType.INSTANCE);
        register(FloatType.INSTANCE);
        register(DoubleType.INSTANCE);
        register(BigDecimalType.INSTANCE);
        register(BigIntegerType.INSTANCE);

        register(StringType.INSTANCE);
        register(StringNVarcharType.INSTANCE);
        register(CharacterNCharType.INSTANCE);
        register(UrlType.INSTANCE);

        register(DurationType.INSTANCE);
        register(InstantType.INSTANCE);
        register(LocalDateTimeType.INSTANCE);
        register(LocalDateType.INSTANCE);
        register(LocalTimeType.INSTANCE);
        register(OffsetDateTimeType.INSTANCE);
        register(OffsetTimeType.INSTANCE);
        register(ZonedDateTimeType.INSTANCE);

        register(DateType.INSTANCE);
        register(TimeType.INSTANCE);
        register(TimestampType.INSTANCE);
        register(DbTimestampType.INSTANCE);
        register(CalendarType.INSTANCE);
        register(CalendarDateType.INSTANCE);

        register(LocaleType.INSTANCE);
        register(CurrencyType.INSTANCE);
        register(TimeZoneType.INSTANCE);
        register(ClassType.INSTANCE);
        register(UUIDBinaryType.INSTANCE);
        register(UUIDCharType.INSTANCE);

        register(BinaryType.INSTANCE);
        register(WrapperBinaryType.INSTANCE);
        register(RowVersionType.INSTANCE);
        register(ImageType.INSTANCE);
        register(CharArrayType.INSTANCE);
        register(CharacterArrayType.INSTANCE);
        register(TextType.INSTANCE);
        register(NTextType.INSTANCE);
        register(BlobType.INSTANCE);
        register(MaterializedBlobType.INSTANCE);
        register(ClobType.INSTANCE);
        register(NClobType.INSTANCE);
        register(MaterializedClobType.INSTANCE);
        register(MaterializedNClobType.INSTANCE);
        register(SerializableType.INSTANCE);

        register(ObjectType.INSTANCE);

        //noinspection unchecked
        register(new AdaptedImmutableType(DateType.INSTANCE));
        //noinspection unchecked
        register(new AdaptedImmutableType(TimeType.INSTANCE));
        //noinspection unchecked
        register(new AdaptedImmutableType(TimestampType.INSTANCE));
        //noinspection unchecked
        register(new AdaptedImmutableType(DbTimestampType.INSTANCE));
        //noinspection unchecked
        register(new AdaptedImmutableType(CalendarType.INSTANCE));
        //noinspection unchecked
        register(new AdaptedImmutableType(CalendarDateType.INSTANCE));
        //noinspection unchecked
        register(new AdaptedImmutableType(BinaryType.INSTANCE));
        //noinspection unchecked
        register(new AdaptedImmutableType(SerializableType.INSTANCE));
    }

    /**
     * Constructor version used during shallow copy
     *
     * @param registeredTypes The type map to copy over
     */
    @SuppressWarnings({"UnusedDeclaration"})
    private BasicTypeRegistry(Map<String, BasicType> registeredTypes) {
        registry.putAll(registeredTypes);
        locked = true;
    }

    public void register(BasicType type) {
        register(type, type.getRegistrationKeys());
    }

    public void register(BasicType type, String[] keys) {
        if (locked) {
            throw new TypeException("Can not alter TypeRegistry at this time");
        }

        if (type == null) {
            throw new TypeException("Type to register cannot be null");
        }

        if (keys == null || keys.length == 0) {
            return;
        }

        for (String key : keys) {
            // be safe...
            if (key == null) {
                continue;
            }
            LOG.debug("Adding type registration [{}] -> [{}]", key, type);
            final Type old = registry.put(key, type);
            if (old != null && old != type) {
            }
        }
    }

    public void register(UserType type, String[] keys) {
        register(new CustomType(type, keys));
    }

    public void register(CompositeUserType type, String[] keys) {
        register(new CompositeCustomType(type, keys));
    }

    public BasicType getRegisteredType(String key) {
        return registry.get(key);
    }

    public BasicTypeRegistry shallowCopy() {
        return new BasicTypeRegistry(this.registry);
    }
}
