package persistence;

import org.json.JSONObject;

// Based on Writable from JsonSerializationDemo
// Represents a foundation for a JSON converter
public interface Writable {
    // EFFECTS: returns this as JSON object
    JSONObject toJson();
}
