const url = `http://localhost:8080/memo/${id}`;
const form = document.querySelector("#form");
// fetch() : window 함수
// get 방식으로 데이터가져올때
fetch(url)
  .then((res) => {
    if (!res.ok) {
      throw new Error(`error! ${res.status}`);
    }

    // json body 추출
    return res.json();
  })
  .then((data) => {
    console.log(data);

    form.mno.value = data.mno;
    form.memo_text.value = data.memoText;
  })
  .catch((err) => console.log(err));
