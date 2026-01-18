package json;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Klasa służąca do przetwarzania JSON, zaimplementowana z użyciem wzorca singleton.
 * Posiada statyczne metody pozwalające na zamianę JSON na obiekt Java, a także obiektu Java na JSON.
 */
public class JSON {
    private static JSON instance;
    private ObjectMapper mapper;

    private JSON() {
        this.mapper = new ObjectMapper();
    }

    /**
     * Metoda służąca do pobierania jedynej istniejącej instancji.
     * Jeśli instancja nie istnieje, zostanie utworzony automatycznie, ale nastąpi to tylko raz przy pierwszym wywołaniu.
     *
     * @return zwraca jedyną istniejącą instancję obiektu
     */
    private static JSON getInstance() {
        if (instance == null) {
            instance = new JSON();
        }
        return instance;
    }

    /**
     * Metoda służąca do zamiany JSON na wybrany obiekt Java. Obiekty proste (pojedyńcza klasa).
     *
     * @param json ciąg znaków JSON
     * @param type klasa, której instancja ma zostać uzupełniona danymi
     * @return obiekt Java, rezultat zamiany JSON
     * @throws JsonProcessingException wyjątek rzucany, gdy JSON lub klasa będzie nieprawidłowy
     */
    public static <T> T parse(String json, Class<T> type) throws JsonProcessingException {
        ObjectMapper mapper = getInstance().getMapper();
        return mapper.readValue(json, type);
    }

    /**
     * Metoda służąca do zamiany JSON na wybrany obiekt Java. Obiekty złożone (np. ArrayList).
     *
     * @param json ciąg znaków JSON
     * @param type klasa, której instancja ma zostać uzupełniona danymi
     * @return obiekt Java, rezultat zamiany JSON
     * @throws JsonProcessingException wyjątek rzucany, gdy JSON lub klasa będzie nieprawidłowy
     */
    public static <T> T parse(String json, TypeReference<T> type) throws JsonProcessingException {
        ObjectMapper mapper = getInstance().getMapper();
        return mapper.readValue(json, type);
    }

    /**
     * Metoda służąca do zamiany obiektu Java na json.JSON.
     *
     * @param object obiekt, który ma zostać zamieniony na JSON
     * @return ciąg znaków JSON
     * @throws JsonProcessingException wyjątek rzucany, gdy zamiana się nie powiedzie
     */
    public static String stringify(Object object) throws JsonProcessingException {
        ObjectMapper mapper = getInstance().getMapper();
        return mapper.writeValueAsString(object);
    }

    /**
     * Metoda zwracająca mapper.
     *
     * @return obiekt mapper
     */
    private ObjectMapper getMapper() {
        return this.mapper;
    }
}