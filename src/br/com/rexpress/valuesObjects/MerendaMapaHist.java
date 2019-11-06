package br.com.rexpress.valuesObjects;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

/**
 * The persistent class for the merenda_mapa_hist database table.
 * 
 */
@Entity
@Table(name = "merenda_mapa_hist")
@NamedQueries({
		@NamedQuery(name = "MerendaMapaHist.findAll", query = "SELECT m FROM MerendaMapaHist m"),
		@NamedQuery(name = "MerendaMapaHist.seExiste", query = "SELECT m FROM MerendaMapaHist m WHERE m.descricaoMerenda = :descricao") })
public class MerendaMapaHist implements Serializable,
		br.com.rexpress.util.VOGenerico {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id_merenda_hist", unique = true, nullable = false)
	private Integer id;

	@Column(name = "descricao_merenda", length = 2147483647)
	private String descricaoMerenda;

	// bi-directional many-to-many association to ItemMapaHist
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "item_merenda_mapa", joinColumns = @JoinColumn(name = "id_merenda_hist", nullable = false), inverseJoinColumns = @JoinColumn(name = "id_item_mapa", nullable = false))
	@Fetch(FetchMode.SUBSELECT)
	private List<ItemMapaHist> itemMapaHists = new ArrayList<ItemMapaHist>();

	@ManyToMany(fetch = FetchType.EAGER, cascade=CascadeType.ALL)
	@JoinTable(name = "descartavel_merenda_mapa", joinColumns = @JoinColumn(name = "id_merenda", nullable = false), inverseJoinColumns = @JoinColumn(name = "id_descartavel", nullable = false))
	@Fetch(FetchMode.SUBSELECT)
	private List<Descartavel> descartavelsMapa = new ArrayList<Descartavel>();
	
	//bi-directional many-to-one association to ItemMapa
	@OneToMany(mappedBy="merendaMapaHist")
	private List<TurnoMapa> turnoMapa = new ArrayList<TurnoMapa>();

	public MerendaMapaHist() {
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getDescricaoMerenda() {
		return this.descricaoMerenda;
	}

	public void setDescricaoMerenda(String descricaoMerenda) {
		this.descricaoMerenda = descricaoMerenda;
	}

	public List<ItemMapaHist> getItemMapaHists() {
		return this.itemMapaHists;
	}

	public void setItemMapaHists(List<ItemMapaHist> itemMapaHists) {
		this.itemMapaHists = itemMapaHists;
	}

	public List<Descartavel> getDescartavelsMapa() {
		return this.descartavelsMapa;
	}

	public void setDescartavelsMapa(List<Descartavel> descartavels) {
		this.descartavelsMapa = descartavels;
	}
	
	public List<TurnoMapa> getItemMapas() {
		return this.turnoMapa;
	}

	public void setTurnoMapas(List<TurnoMapa> turnoMapas) {
		this.turnoMapa = turnoMapas;
	}

	public TurnoMapa addTurnoMapa(TurnoMapa turnoMapa) {
		getItemMapas().add(turnoMapa);
		turnoMapa.setMerendaMapaHist(this);

		return turnoMapa;
	}

	public TurnoMapa removeTurnoMapa(TurnoMapa turnoMapa) {
		getItemMapas().remove(turnoMapa);
		turnoMapa.setMerendaMapaHist(null);

		return turnoMapa;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime
				* result
				+ ((descricaoMerenda == null) ? 0 : descricaoMerenda.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof MerendaMapaHist))
			return false;
		MerendaMapaHist other = (MerendaMapaHist) obj;
		if (descricaoMerenda == null) {
			if (other.descricaoMerenda != null)
				return false;
		} else if (!descricaoMerenda.equals(other.descricaoMerenda))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	public MerendaMapaHist valueOf(Merenda pMerenda) {
		MerendaMapaHist merendaHist = new MerendaMapaHist();
		ItemMapaHist itemHist = new ItemMapaHist();
		merendaHist.setDescricaoMerenda(pMerenda.getDescricaoMerenda());
		merendaHist.setItemMapaHists(itemHist.valueOf(pMerenda.getItems()));
		merendaHist.setDescartavelsMapa(new ArrayList<Descartavel>(pMerenda.getDescartavels()));
		return merendaHist;
	}
}