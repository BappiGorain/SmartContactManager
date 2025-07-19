console.log("contacts.js loaded");

const baseURL = "http://localhost:8080";

// Modal root element
const viewContactModal = document.getElementById("view_contact_modal");

// ---- Flowbite Modal options ----
const options = {
  placement: 'center', // better UX than bottom-right
  backdrop: 'dynamic',
  backdropClasses: 'bg-gray-900/50 dark:bg-gray-900/80 fixed inset-0 z-40',
  closable: true,
  onHide: () => console.log('modal hidden'),
  onShow: () => console.log('modal shown'),
  onToggle: () => console.log('modal toggled'),
};

const instanceOptions = {
  id: 'view_contact_modal',
  override: true,
};

// Guard: Flowbite must be loaded
if (typeof Modal === 'undefined') {
  console.error('Flowbite Modal class not found. Include flowbite.min.js before contacts.js');
}

// Create modal instance (if element exists)
const contactModal = viewContactModal
  ? new Modal(viewContactModal, options, instanceOptions)
  : null;

// Public modal controls
function openContactModal() {
  if (!contactModal) return;
  contactModal.show();
}

function closeContactModal() {
  if (!contactModal) return;
  contactModal.hide();
}

// ---------------- Utilities ---------------- //

function normalizeUrl(url) {
  if (!url) return null;
  url = url.trim();
  if (!url) return null;
  if (!/^https?:\/\//i.test(url)) {
    return 'https://' + url;
  }
  return url;
}

function setText(id, value, fallback = '—') {
  const el = document.getElementById(id);
  if (!el) return;
  el.textContent = (value !== null && value !== undefined && value !== '') ? value : fallback;
}

function setLink(id, url, fallback = '—') {
  const el = document.getElementById(id);
  if (!el) return;
  const normalized = normalizeUrl(url);
  if (!normalized) {
    el.removeAttribute('href');
    el.textContent = fallback;
    el.classList.add('pointer-events-none', 'opacity-50');
  } else {
    el.href = normalized;
    el.textContent = url;
    el.classList.remove('pointer-events-none', 'opacity-50');
  }
}

function setImage(id, url) {
  const el = document.getElementById(id);
  if (!el) return;
  el.src = (url && url.trim() !== '') ? url : '/images/default-avatar.png';
  el.alt = 'Contact picture';
}

// ---------------- Populate Modal ---------------- //

function populateContactModal(data) {
  setText('contact_name', data.name, 'No name');
  setText('contact_email', data.email, '');

  setText('contact_phoneNumber', data.phoneNumber);
  setText('contact_address', data.address);
  setText('contact_description', data.description);

  const favEl = document.getElementById('contact_favorite');
  if (favEl) {
    favEl.textContent = data.favorite ? '⭐ Yes' : 'No';
  }

  setLink('contact_websiteLink', data.websiteLink);
  setLink('contact_linkedInLink', data.linkedInLink);

  setImage('contact_picture', data.picture);
}

// ---------------- Fetch + Show ---------------- //

async function loadContactdata(id) {
  console.log("loadContactdata:", id);
  try {
    const resp = await fetch(`${baseURL}/api/contacts/${id}`);
    if (!resp.ok) {
      throw new Error(`HTTP ${resp.status}`);
    }
    const data = await resp.json();
    console.log("contact data:", data);

    populateContactModal(data);
    openContactModal();
  } catch (error) {
    console.error("Error loading contact:", error);
  }
}

// ---------------- Delete Contact ---------------- //



async function deleteContact(id) {
  console.log("Delete contact:", id);

  Swal.fire({
    title: "Do you want to delete the contact?",
    text: "You won't be able to revert this!",
    icon: "warning",
    showCancelButton: true,
    confirmButtonText: "Yes, delete it!",
    cancelButtonText: "No, cancel!",
    reverseButtons: true,
    customClass: {
      confirmButton: "bg-red-600 hover:bg-red-700 text-white font-semibold px-4 py-2 rounded mx-2",
      cancelButton: "bg-gray-300 hover:bg-gray-400 text-black font-semibold px-4 py-2 rounded mx-2"
    },
    buttonsStyling: false
  }).then((result) => {
    if (result.isConfirmed) {
      Swal.fire({
        title: "Deleted!",
        text: "Your contact has been deleted.",
        icon: "success",
        confirmButtonText: "OK",
        customClass: {
          confirmButton: "bg-green-600 hover:bg-green-700 text-white font-semibold px-4 py-2 rounded"
        },
        buttonsStyling: false
      });

      // Add 1 second delay before redirect
      setTimeout(() => {
        const url = `${baseURL}/user/contacts/delete/${id}`;
        window.location.replace(url);
      }, 1000);

    } else if (result.dismiss === Swal.DismissReason.cancel) {
      Swal.fire({
        title: "Cancelled",
        text: "Your contact is safe :)",
        icon: "error",
        confirmButtonText: "OK",
        customClass: {
          confirmButton: "bg-blue-600 hover:bg-blue-700 text-white font-semibold px-4 py-2 rounded"
        },
        buttonsStyling: false
      });
    }
  });
}



// ---------------- Expose to inline HTML (important!) ---------------- //
// If your script loads before the buttons, attach to window so inline onclick works.
window.loadContactdata = loadContactdata;
window.deleteContact = deleteContact;
window.openContactModal = openContactModal;
window.closeContactModal = closeContactModal;
