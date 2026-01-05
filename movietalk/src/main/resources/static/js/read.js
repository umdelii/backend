// review
const baseUrl = "/reviews";
const reviewListClass = document.querySelector(".review-list");
const reviewCnt = document.querySelector(".review-cnt");
const reviewForm = document.querySelector("#review-form");

// 날짜&시간 이쁘게 띄우기
const formatDate = (data) => {
  const date = new Date(data);
  // 2025-12-16 15:26
  return (
    date.getFullYear() +
    "-" +
    (date.getMonth() + 1) +
    "-" +
    date.getDate() +
    " " +
    date.getHours() +
    ":" +
    date.getMinutes()
  );
};

// 口コミ全部
const reviewList = () => {
  fetch(`${baseUrl}/${mno}/all`)
    .then((res) => {
      if (!res.ok) {
        throw new Error(res.get());
      }
      return res.json();
    })
    .then((data) => {
      console.log(data);

      let result = "";
      data.forEach((review) => {
        result += `<div class="d-flex justify-content-between py-2 border-bottom review-row" data-rno="${review.rno}" data-email="${review.email}">`;
        result += `<div class="flex-grow-1 align-self-center">`;
        result += `<div><span class="font-semibold">${review.text}</span></div>`;
        result += `<div class="small text-muted"><span class="d-inline-block mr-3">${review.nickname}</span>`;
        result += `평점 : <span class="grade">${review.grade}</span><div class="starrr"></div></div>`;
        result += `<div class="text-muted"><span class="small">${formatDate(
          review.createDate
        )}</span></div></div>`;
        if (loginUser == `${review.email}`) {
          result += `<div class="d-flex flex-column align-self-center">`;
          result += `<div><button class="btn btn-outline-success btn-sm">変更</button></div>`;
          result += `<div class="mb-2"><button class="btn btn-outline-danger btn-sm">修正</button></div>`;
          result += `</div>`;
        }
        result += `</div>`;
      });

      reviewListClass.innerHTML = result;
      reviewCnt.innerHTML = data.length;
    })
    .catch((e) => console.error(e));
};

reviewList();

// 特定の口コミ取得
const reviewGet = (rno) => {
  fetch(`${baseUrl}/${mno}/${rno}`)
    .then((res) => {
      if (!res.ok) {
        throw new Error(res.get());
      }
      return res.json();
    })
    .then((data) => {
      console.log("get");
      console.log(data);

      // #authenticationからデータ取得
      // reviewForm.nickname.value = data.nickname;
      reviewForm.text.value = data.text;
      reviewForm.rno.value = data.rno;
      reviewForm.mid.value = data.mid;
      reviewForm.mno.value = data.mno;
      reviewForm.email.value = data.email;
      reviewForm
        .querySelector(".starrr a:nth-child(" + data.grade + ")")
        .click();
    })
    .catch((e) => console.error(e));
};

// delete(口コミ削除)関数
const reviewDelete = (rno, email) => {
  const form = new FormData();
  form.append("email", email);

  fetch(`${baseUrl}/${mno}/${rno}`, {
    method: "delete",
    headers: { "X-CSRF-TOKEN": csrfVal },
    body: form,
  })
    .then((res) => {
      if (!res.ok) {
        throw new Error(res.get());
      }
      return res.text();
    })
    .then((data) => {
      console.log("delete");
      console.log(data);
      // 画面f5
      reviewList();
    })
    .catch((e) => console.error(e));
};

// 変更(または削除)ボタン押す
reviewListClass.addEventListener("click", (e) => {
  const btn = e.target;
  const rno = btn.closest(".review-row").getAttribute("data-rno");
  const email = btn.closest(".review-row").getAttribute("data-email");
  console.log(btn);

  if (btn.classList.contains("btn-outline-danger")) {
    reviewDelete(rno, email);
  } else if (btn.classList.contains("btn-outline-success")) {
    reviewGet(rno);
  }
});

// ------口コミ put 関数
const reviewPut = (form, rno) => {
  fetch(`${baseUrl}/${mno}/${rno}`, {
    method: "put",
    headers: { "Content-Type": "application/json", "X-CSRF-TOKEN": csrfVal },
    body: JSON.stringify({
      rno: rno,
      text: form.text.value,
      grade: grade,
      email: form.email.value,
    }),
  })
    .then((res) => {
      if (!res.ok) {
        throw new Error(res.get());
      }
      return res.text();
    })
    .then((data) => {
      console.log("modify");
      console.log(data);

      form.rno.value = "";
      form.nickname.value = "";
      form.mid.value = "";
      form.text.value = "";
      form.querySelector(".starrr a:nth-child(" + grade + ")").click();

      reviewList();
    })
    .catch((e) => console.error(e));
};

// 口コミ post 関数
const reviewPost = (form, rno) => {
  fetch(`${baseUrl}/${mno}`, {
    method: "post",
    headers: { "Content-Type": "application/json", "X-CSRF-TOKEN": csrfVal },
    body: JSON.stringify({
      mno: mno,
      mid: reviewForm.mid.value,
      text: reviewForm.text.value,
      grade: grade,
      rno: rno,
    }),
  })
    .then((res) => {
      if (!res.ok) {
        throw new Error(res.get());
      }
      return res.text();
    })
    .then((data) => {
      console.log("new!");
      console.log(data);

      form.rno.value = "";
      form.nickname.value = "";
      form.text.value = "";
      form.querySelector(".starrr a:nth-child(" + grade + ")").click();

      reviewList();
    })
    .catch((e) => console.error(e));
};

// 登録OR修正
if (reviewForm) {
  reviewForm.addEventListener("submit", (e) => {
    e.preventDefault();
    // rno 有無
    const form = e.target;
    const rno = form.rno.value;

    if (rno) {
      // 修正
      reviewPut(form, rno);
    } else if (rno !== null) {
      // 新規
      reviewPost(form, rno);
    }
  });
}

// image 拡大
const imgModal = document.getElementById("imgModal");
if (imgModal) {
  imgModal.addEventListener("show.bs.modal", (event) => {
    // modal을 뜨게 한 li요소 찾기
    const posterLi = event.relatedTarget;
    // Extract info from data-bs-* attributes
    const filePath = posterLi.getAttribute("data-file");
    // If necessary, you could initiate an Ajax request here
    // and then do the updating in a callback.

    // Update the modal's content.
    const modalTitle = imgModal.querySelector(".modal-title");
    const modalBody = imgModal.querySelector(".modal-body");

    modalTitle.textContent = `${title}`;
    modalBody.innerHTML = `<img src="/upload/display?fileName=${filePath}" style="width:100%" />`;
  });
}
