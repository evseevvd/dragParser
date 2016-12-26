package drag.parser.entity;

/**
 * Created by evseevvd on 26.12.16.
 */

public class Pill {

    /**
     * Статический индинтификатор в РЛС
     */
    private String id;
    /**
     * Наименоване лекарства
     */
    private String name;
    /**
     * Ссылка на карточку ЛС
     */
    private String url;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
