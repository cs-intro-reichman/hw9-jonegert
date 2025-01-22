/**
 * Represents a list of Nodes. 
 */
public class LinkedList {

    private Node first; // pointer to the first element of this list
    private Node last;  // pointer to the last element of this list
    private int size;   // number of elements in this list

    /**
     * Constructs a new list.
     */
    public LinkedList () {
        first = null;
        last = null;
        size = 0;
    }

    /**
     * Gets the first node of the list
     */
    public Node getFirst() {
        return this.first;
    }

    /**
     * Gets the last node of the list
     */
    public Node getLast() {
        return this.last;
    }

    /**
     * Gets the current size of the list
     */
    public int getSize() {
        return this.size;
    }

    /**
     * Gets the node located at the given index in this list.
     *
     * @param index the index of the node to retrieve, between 0 and size
     * @throws IllegalArgumentException if index is negative or greater than the list's size
     * @return the node at the given index
     */
    public Node getNode(int index) {
        if (index < 0 || index > size) {
            throw new IllegalArgumentException("index must be between 0 and size");
        }
        Node current = this.first;
        for(int i=0; i<index; i++){
            current = current.next;
        }
        return current; // יכול להיות null אם index==size, אבל לעתים זה לגיטימי כמצב "אחרי האחרון"
    }

    /**
     * Creates a new Node object that points to the given memory block,
     * and inserts the node at the given index in this list.
     *
     * @param block the memory block to be inserted
     * @param index the index before which the memory block should be inserted
     */
    public void add(int index, MemoryBlock block) {
        if (index < 0 || index > size) {
            throw new IllegalArgumentException("index must be between 0 and size");
        }
        Node newNode = new Node(block);
        // מקרה רשימה ריקה
        if(size == 0) {
            first = newNode;
            last  = newNode;
        }
        // הוספה לראש הרשימה (index == 0)
        else if (index == 0) {
            newNode.next = first;
            first = newNode;
        }
        // הוספה לסוף (index == size)
        else if (index == size) {
            last.next = newNode;
            last = newNode;
        }
        // הוספה באמצע
        else {
            Node prev = getNode(index-1); // guaranteed חוקי
            newNode.next = prev.next;
            prev.next = newNode;
        }
        size++;
        // עדכון last אם צריך (מקרה של size==1, או הוספה בסוף)
        if (newNode.next == null) {
            last = newNode;
        }
    }

    /**
     * Creates a new node for the block and adds it to the end of the list
     */
    public void addLast(MemoryBlock block) {
        add(size, block);
    }

    /**
     * Creates a new node for the block and adds it to the beginning of the list
     */
    public void addFirst(MemoryBlock block) {
        add(0, block);
    }

    /**
     * Gets the memory block located at the given index
     */
    public MemoryBlock getBlock(int index) {
        Node n = getNode(index);
        if(n == null) {
            // אם index == size, נחזיר null או נזרוק חריגה (תלוי בדרישות).
            throw new IllegalArgumentException("index must be between 0 and size-1");
        }
        return n.block;
    }

    /**
     * Gets the index of the node pointing to the given memory block,
     * or -1 if not found
     */
    public int indexOf(MemoryBlock block) {
        Node current = this.first;
        int i = 0;
        while (current != null) {
            if (current.block == block) {
                return i;
            }
            current = current.next;
            i++;
        }
        return -1;
    }

    /**
     * Removes the node at the given index
     */
    public void remove(int index) {
        if(index < 0 || index >= size){
            throw new IllegalArgumentException("index must be between 0 and size-1");
        }
        if(index == 0) {
            // הסרה של הראשון
            first = first.next;
            if(first == null) {
                last = null; // הרשימה התרוקנה
            }
        } else {
            Node prev = getNode(index - 1);
            Node toRemove = prev.next; // לא אמור להיות null
            prev.next = toRemove.next;
            if(toRemove == last) {
                last = prev; // הסרנו את האחרון
            }
        }
        size--;
    }

    /**
     * Removes the given node from the list
     */
    public void remove(Node node) {
        // קודם נמצא את האינדקס שלו
        int x = indexOf(node.block);
        if(x == -1) {
            // חסר ברשימה
            throw new IllegalArgumentException("index must be between 0 and size-1");
        }
        remove(x);
    }

    /**
     * Removes from this list the node pointing to the given memory block.
     */
    public void remove(MemoryBlock block) {
        int x = indexOf(block);
        if(x == -1) {
            throw new IllegalArgumentException("index must be between 0 and size-1");
        }
        remove(x);
    }

    /**
     * Returns an iterator over this list, starting with the first element.
     */
    public ListIterator iterator(){
        return new ListIterator(first);
    }

	/**
	 * A textual representation of this list, for debugging.
	 */
	public String toString() {
		String str="";
		if(size==0){
			return "[]";
		}Node currrent=this.first;
		for(int i=0;i<size;i++){
			str+=" [" + currrent.block + "] " ;
			currrent=currrent.next;
		}
		return str;
	}
}