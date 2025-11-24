export function isToolInFavorite(toolId, favoriteList) {
    //if toolId parameter is in the favorites list from the employee, it returns true
    let result = favoriteList.some(favorite => favorite.id === toolId);
    return result;
}
