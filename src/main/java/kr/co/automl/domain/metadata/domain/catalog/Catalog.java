package kr.co.automl.domain.metadata.domain.catalog;

import kr.co.automl.domain.metadata.domain.catalog.converter.CatagoryConverter;
import kr.co.automl.domain.metadata.domain.catalog.converter.ThemeConverter;
import kr.co.automl.domain.metadata.domain.catalog.dto.CreateCatalogAttributes;
import kr.co.automl.domain.metadata.domain.dataset.DataSet;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;

import static lombok.AccessLevel.PROTECTED;

/**
 * 카탈로그.
 *
 * 카테고리 목록들을 가지고 있습니다.
 */
@Entity
@Getter
@NoArgsConstructor(access = PROTECTED)
public class Catalog {

    @Id
    @GeneratedValue
    @Column(name = "catalog_id")
    private long id;

    @Convert(converter = CatagoryConverter.class)
    private Category category;

    @Convert(converter = ThemeConverter.class)
    private Theme theme;

    private String themeTaxonomy;

    @OneToOne(mappedBy = "catalog")
    private DataSet dataSet;

    @Builder
    private Catalog(Category category, Theme theme, String themeTaxonomy) {
        this.category = category;
        this.theme = theme;
        this.themeTaxonomy = themeTaxonomy;
    }

    /**
     * 생성한 카탈로그를 리턴합니다.
     * @param attributes 카탈로그 생성 시 필요한 요소들
     * @return 생성한 카탈로그
     */
    public static Catalog from(CreateCatalogAttributes attributes) {
        Category category = Category.ofName(attributes.category());
        Theme theme = category.findThemeByName(attributes.theme());

        return Catalog.builder()
                .category(category)
                .theme(theme)
                .themeTaxonomy(attributes.themeTaxonomy())
                .build();
    }

    public void setRelation(DataSet dataSet) {
        this.dataSet = dataSet;
    }
}
