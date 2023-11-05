import React, { useContext, useEffect, useState } from "react";
import style from "./Search.module.scss";
import FooterMobile from "../../components/footer/FooterMobile";
import { searchUsers } from "../../apis/search";
import { useNavigate } from "react-router-dom";
import { getAllGenders } from "../../apis/genders";
import { useSearchResults } from "../../context/SearchResultsContext";

const Search = () => {
  const { setSearchResults } = useSearchResults() ; 
  const [searchCriteria, setSearchCriteria] = useState({
    pseudo: "",
    town: "",
    genderId: "",
    gitProfile: "",
  });

  const [genders, setGenders] = useState([]);
  const [error, setError] = useState("");
  const navigate = useNavigate();

  useEffect(() => {
    async function fetchGenders() {
      try {
        const genders = await getAllGenders();
        setGenders(genders);
      } catch (error) {
        // Gérez les erreurs ici
        console.error("Erreur lors de la récupération des genres :", error);
      }
    }

    fetchGenders();
  }, []);

  const handleInputChange = (e) => {
    const { name, value } = e.target;
    setSearchCriteria({ ...searchCriteria, [name]: value });
  };

  const handleSearch = async () => {
    if (
      !searchCriteria.pseudo &&
      !searchCriteria.town &&
      !searchCriteria.genderId &&
      !searchCriteria.gitProfile
    ) {
      // Aucun critère n'est sélectionné, affichez un message d'erreur ou prenez l'action appropriée
      setError("Veuillez sélectionner au moins un critère de recherche.");
      return;
    }
    try {
      const data = await searchUsers(searchCriteria);
      setSearchResults(data);

      setSearchCriteria({
        pseudo: "",
        town: "",
        gitProfile: "",
      });

      navigate("/results", {
        state: {
          searchResults: data,
        },
      });
      setError("");
    } catch (error) {
      // Gérez les erreurs ici
      console.error("Erreur de recherche :", error);
    }
  };

  const handleGenderChange = (e) => {
    const { value } = e.target;
    setSearchCriteria({ ...searchCriteria, genderId: value });
  };

  return (
    <div className={style.search}>
      <div className={style.inputSearch}>
        <h1>Vous pouvez chercher des profils par ces critères:</h1>
        {error && <div className={style.errorText}>{error}</div>}
        <div className={style.checkbox}>
          <h2>Genre :</h2>
          {genders.map((gender) => (
            <label key={gender.id}>
              <input
                type="radio"
                name="genderId"
                value={gender.id.toString()}
                checked={searchCriteria.genderId === gender.id.toString()}
                onChange={handleGenderChange}
              />
              {gender.name}
            </label>
          ))}
        </div>
        <input
          type="text"
          name="pseudo"
          placeholder="pseudo"
          value={searchCriteria.pseudo}
          onChange={handleInputChange}
        />
        <input
          type="text"
          name="town"
          placeholder="ville"
          value={searchCriteria.town}
          onChange={handleInputChange}
        />
        <input
          type="text"
          name="gitProfile"
          placeholder="pseudo Git"
          value={searchCriteria.gitProfile}
          onChange={handleInputChange}
        />
        <div className={style.buttonSearch}>
          <button onClick={handleSearch}>Chercher</button>
        </div>
      </div>
      <FooterMobile />
    </div>
  );
};

export default Search;
