import { createContext, useContext, useState } from 'react';

const SearchResultsContext = createContext();

export function useSearchResults() {
  return useContext(SearchResultsContext);
}

export function SearchResultsProvider({ children }) {
  const [searchResults, setSearchResults] = useState([]);
  
  return (
    <SearchResultsContext.Provider value={{ searchResults, setSearchResults }}>
      {children}
    </SearchResultsContext.Provider>
  );
}

