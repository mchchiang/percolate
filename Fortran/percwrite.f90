module percwrite

  use percsort
  implicit none

  private
  public :: perc_write

contains

  !
  ! DO NOT CHANGE THIS FILE
  !
  ! Routine to write percolation data to file
  !
  ! Clusters are coloured by size with the largest "maxcluster" being
  ! displayed.
  !

  subroutine perc_write(n, percmap, percfile, maxcluster)

    integer, intent(in)                          :: n
    integer, dimension(0:n+1, 0:n+1), intent(in) :: percmap
    character (len = *), intent(in)              :: percfile
    integer, intent(in)                          :: maxcluster

    integer :: lmaxcluster

    integer :: i, j
    integer :: ncluster, maxsize, colour
    integer, parameter :: fmtlen = 32

    character (len = fmtlen)  :: fmtstring

    integer, dimension(n*n)   :: rank
    integer, dimension(2,n*n) :: clustlist
    integer, dimension(n)     :: pgmline

    ! clustlist(1,:) is the size of the cluster, clustlist(2,:) is its id

    integer :: iounit = 12

    do i = 1, n*n

       rank(i) = -1

       clustlist(1,i) = 0
       clustlist(2,i) = i

    end do

    !
    ! Find the sizes of all the distinct clusters
    !

    do i = 1, n
       do j = 1, n

          if (percmap(i,j) > 0) then
             clustlist(1,percmap(i,j)) = clustlist(1,percmap(i,j)) + 1
          end if

       end do
    end do

    !
    ! Now sort them with the largest first
    !

    call perc_sort(clustlist, n*n)

    maxsize = clustlist(1,1)

    ncluster = 0

    do while (ncluster < n*n .and. clustlist(1,ncluster+1) > 0)
       ncluster = ncluster + 1
    end do

    lmaxcluster = maxcluster

    if (lmaxcluster > ncluster) then
       lmaxcluster = ncluster
    end if

    do i = 1, ncluster
       rank(clustlist(2,i)) = i
    end do

    write(*,*) 'percwrite: opening file ', percfile

    open(unit=iounit, file=percfile)

    write(*,*) 'percwrite: map has ', ncluster, &
         ' clusters, maximum cluster size is ', maxsize

    if (lmaxcluster == 1) then
       write(*,*) 'percwrite: displaying the largest cluster'
    else if (lmaxcluster == ncluster) then
       write(*,*) 'percwrite: displaying all clusters'
    else
       write(*,*) 'percwrite: displaying largest ', lmaxcluster, ' clusters'
    end if

    write(*,*) 'percwrite: writing data ...'

    write(fmtstring, fmt='(''('', i3, ''(1x, i3))'')') n
    write(iounit,fmt='(''P2'')')
    write(iounit,*) n,  n

    if (lmaxcluster > 0) then
       write(iounit,*) lmaxcluster
    else
       write(iounit,*) 1
    end if

    do j = n, 1, -1
       do i = 1, n

          colour = percmap(i,j)

          !
          ! If it is part of a cluster, look for the colour
          !

          if (percmap(i,j) > 0) then

             colour = rank(percmap(i,j)) - 1

             if (colour >= lmaxcluster) then
                colour = lmaxcluster
             end if

          else
             colour = lmaxcluster
          end if

          pgmline(i) = colour

       end do

       write(iounit,fmt=fmtstring) (pgmline(i), i=1,n)

    end do

    write(*,*) 'percwrite: ... done'

    close(iounit)
    write(*,*) 'percwrite: file closed'

  end subroutine perc_write

end module percwrite
