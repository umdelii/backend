const url = "http://localhost:8080/memo";
// fetch() : window 함수
// get 방식으로 데이터가져올때
fetch(url)
  .then((res) => {
    if (!res.ok) {
      throw new Error(`error! ${res.status}`);
    }
    return res.json();
  })
  .then((data) => {
    console.log(data);

    let result = "";
    data.forEach((memo) => {
      result += `<tr>`;
      result += `<th scope="row">${memo.mno}</th>`;
      result += `<td>`;
      result += `<a href="/memo/read2?mno=${memo.mno}">${memo.memoText}</a>`;
      result += `</td>`;
      result += `<td>${memo.createDate}</td>`;
      result += `<td>${memo.updateDate}</td>`;
      result += `</tr>`;
    });

    document
      .querySelector("table tbody")
      .insertAdjacentHTML("afterbegin", result);
  })
  .catch((err) => console.log(err));
